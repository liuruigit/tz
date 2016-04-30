package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.cache.SessionCache;
import com.jhl.common.BalanceChangeType;
import com.jhl.common.InvestStatus;
import com.jhl.constant.SystemConstant;
import com.jhl.dao.AccountChangeDao;
import com.jhl.dao.AccountDao;
import com.jhl.dao.IBillingDao;
import com.jhl.dao.InvestOrderDao;
import com.jhl.dao.impl.rmb.HlbDao;
import com.jhl.dao.impl.rmb.ProjectDao;
import com.jhl.db.SQLOperator;
import com.jhl.dto.UserBalanceDTO;
import com.jhl.exception.AppException;
import com.jhl.exception.AppExceptionType;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.AccountChange;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojo.biz.Project;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.security.JwtHolder;
import com.jhl.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by vic wu on 2015/8/14.
 */
@Service(value = "accountService")
@Transactional
public class AccountService extends BaseService<Account> {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ContractGenerateService contractGenerateService;
    @Autowired
    private HlbDao hlbDao;
    @Autowired
    private IBillingDao billingDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    AccCouponService accCouponService;
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    SessionCache sessionCache;
    @Autowired
    MessageService messageService;

    @Autowired
    private InvestOrderDao investOrderDao;

    @Autowired
    private AccountChangeDao accountChangeDao;


    /**
     * 采用乐观锁 + 悲观锁的机制更新由户的资金信息
     *
     * @param userId
     * @param handlerMoney 操作金额为0,则传new Money()
     * @param type         {@link AppExceptionType}
     * @throws Exception
     */
    public void updateUserBalance(Integer userId, Money handlerMoney,Money settlMoney,
                                  BalanceChangeType type,String orderNo,String... extraMsgs)throws Exception {
        int result = 0;
        /**
         * 乐观锁尝试5次
         */
        for (int i = 0; i < 5; i++) {
            Account account = null;
            try {
                account = accountDao.queryById(Account.class, userId);
                if(account.modifiedByException()){
                    throw new AppException(AppExceptionType.INVEST_ACCOUNT_EXCEPTION);
                }
            } catch (Exception e) {
                logger.error(SessionUtil.getNo() + "查询帐户信息时出错", e);
                throw new AppException(AppExceptionType.INVEST_ACCOUNT_EXCEPTION);
            }
            result = updateBalance(handlerMoney,settlMoney,type, account);
            if (result == 1) break;
        }
        /**
         * 悲观锁尝试
         */
        if (result == 0) {
            logger.info("乐观锁更新失败,进入悲观锁");
            Account account = accountDao.queryAccountForLock(userId);
            result = updateBalance(handlerMoney,settlMoney, type, account);
        }

        if (result == 0) {
            throw new AppException(AppExceptionType.INVEST_ACCOUNT_EXCEPTION);
        }

        addAccountChangeRecord(userId,handlerMoney.add(settlMoney),type,orderNo,extraMsgs);
    }

    public Account queryAccByIdNo(String idNo) throws Exception{
        return accountDao.isExistsCertNo(DESUtil.getEncString(idNo));
    }

    public void updateUserBalance(Integer userId, Money handlerMoney, BalanceChangeType type,String orderNo,String... extraMsg) throws Exception{
        updateUserBalance( userId, handlerMoney,new Money(0), type,orderNo,extraMsg);
        if (!Strings.isNullOrEmpty(sessionCache.getStr(userId.toString()))){
            updateSession(userId);
        }
    }


    /**
     * 插入资金变化记录数据
     * @param id
     * @param handlerMoney
     * @param type
     * @param orderNo
     */
    public void addAccountChangeRecord(Integer id,Money handlerMoney,BalanceChangeType type,String orderNo,String ... extraMsgs) throws Exception{
        Account account = accountDao.queryById(Account.class,id);
        AccountChange changeRecord = new AccountChange();
        changeRecord.setUserId(id);
        changeRecord.setAmount(handlerMoney.getCent());
        changeRecord.setCreateTime(new Date());
        changeRecord.setTranName(type.getVal());
        changeRecord.setType(type.getType(type));
        changeRecord.setOrderNo(orderNo);
        changeRecord.setAccMoney(account.getMoney());
        try {
            setExtraMsg(changeRecord,extraMsgs);
            accountChangeDao.add(changeRecord);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "插入资金变化记录失败", e);
            throw new AppException(AppExceptionType.INVEST_ACCOUNT_EXCEPTION);
        }
    }

    private void setExtraMsg(AccountChange changeRecord ,String... extraMsg) throws Exception{
        if (extraMsg == null) return;
        switch (extraMsg.length){
            case 0:
                break;
            case 1:
                changeRecord.setExtraMsg1(extraMsg[0]);
                break;
            case 2:
                changeRecord.setExtraMsg1(extraMsg[0]);
                changeRecord.setExtraMsg2(extraMsg[1]);

                break;
            case 3:
                changeRecord.setExtraMsg1(extraMsg[0]);
                changeRecord.setExtraMsg2(extraMsg[1]);
                changeRecord.setExtraMsg3(extraMsg[2]);
                break;
            case 4:
                changeRecord.setExtraMsg1(extraMsg[0]);
                changeRecord.setExtraMsg2(extraMsg[1]);
                changeRecord.setExtraMsg3(extraMsg[2]);
                changeRecord.setExtraMsg4(extraMsg[3]);
                break;
            default:
                logger.warn("错误的消息调用："+extraMsg.length);
                break;
        }
    }

    /**
     * 更新帐户的资金信息
     *
     * @param handlerMoney
     * @param type
     * @param account
     * @return
     */
    private int updateBalance(Money handlerMoney,Money settlMoney, BalanceChangeType type, Account account) throws Exception{
        int result;
        long balanceAfterUpdate = account.getMoney();
        UserBalanceDTO userBalanceDTO = new UserBalanceDTO(account);

        /**
         * 详见对账设计文档
         */
        switch (type) {
            case CHARGE:
                balanceAfterUpdate += handlerMoney.getCent();
                break;
            case PRE_INVEST:
                balanceAfterUpdate -= handlerMoney.getCent();
                userBalanceDTO.setFrozenMoney(account.getFrozenMoney() + handlerMoney.getCent());
                break;
            case INVEST:
                userBalanceDTO.setFrozenMoney(account.getFrozenMoney() - handlerMoney.getCent());
                userBalanceDTO.setInvestMoney(account.getInvestMoney() + handlerMoney.getCent());
                break;
            case TRAN:
                balanceAfterUpdate += handlerMoney.getCent() + settlMoney.getCent();
                userBalanceDTO.setAccIncome(account.getAccIncome() + settlMoney.getCent());
                break;
            case Settlement:
                balanceAfterUpdate += handlerMoney.getCent() + settlMoney.getCent();
                userBalanceDTO.setAccIncome(account.getAccIncome() + settlMoney.getCent());
                break;
            case PRE_WITHDRAW:
                balanceAfterUpdate -= handlerMoney.getCent();
                userBalanceDTO.setFrozenMoney(account.getFrozenMoney() + handlerMoney.getCent());
                break;
            case WITHDRAW:
                userBalanceDTO.setFrozenMoney(account.getFrozenMoney() - handlerMoney.getCent());
                break;
        }

        if (balanceAfterUpdate < 0) {
            logger.error(SessionUtil.getNo() + "帐户余额不足");
            throw new AppException(AppExceptionType.INVEST_BALANCE_NOT_ENOUGH);
        }
        userBalanceDTO.setUserId(account.getId());
        userBalanceDTO.setBalance(balanceAfterUpdate);
        userBalanceDTO.setVersion(account.getVersion());
        userBalanceDTO.generateDigest();
        result = accountDao.updateBalance(userBalanceDTO);
        return result;
    }

    /**
     * 用户投资
     *
     * @param projectId
     * @param amount 单位是 元
     * @param clientVersion
     * @throws Exception
     */
    public void doInvest(Integer userId, String accountName, Integer projectId, double amount, String clientVersion,
                         String proName,String couponId) throws Exception {

        Account session = SessionUtil.getSession();

        Money investMoney = new Money(amount);

        /**
         * 检查投资项目的投资条件是否符合
         */
        Project project = projectDao.queryById(Project.class, projectId);

        validateInvestInfo(amount, project,session.getVip());

        /**
         * 更新项目已融资金额
         */
        int result = projectDao.updateInvestAmount(projectId, investMoney);
        if (result == 0) {
            logger.error(SessionUtil.getNo() + "投资失败,项目的可投余额已不足");
            throw new AppException(AppExceptionType.INVEST_PROJECT_BALANCE_NOT_ENOUGH);
        }
        /**
         * 检查是否满标,如果满标则更新标的状态
         */
        projectDao.updateStatusIfFull(projectId);

        /**
         * 创建投资订单
         */
        InvestOrder record = buildInvestOrder(userId, accountName, projectId, new Money(amount), clientVersion);

        /**
         * 计算投资券抵扣
         */
        Money payMoney = accCouponService.couponDiscount(amount,couponId,record);

        logger.info("开始插入投资记录,{}", record);
        investOrderDao.add(record);

        /**
         * 采用乐观锁 + 悲观锁机制更新帐户信息
         */
        updateUserBalance(userId, payMoney, BalanceChangeType.PRE_INVEST,record.getNo(),record.getNo(),proName,project.getNo());

        /**
         * 更新葫芦币,按照投资金额，不减扣投资券
         * */
        accountDao.updateHlb(userId,amount);

        hlbDao.addChangeDao(session,amount,"投资");

        logger.info("投资时更新帐户信息成功,Success!");

        /**
         * 发送信息
         */
        messageService.sendInvest(project.getNo(),proName,amount,session);

        /**
         * 保存投资协议书
         */
        contractGenerateService.asyGenInvestContract(record,project,session);
    }

    /**
     * 构造投资记录对象
     *
     * @param userId
     * @param accountName
     * @param projectId
     * @param amount
     * @param clientVersion
     * @return
     */
    private InvestOrder buildInvestOrder(Integer userId, String accountName, Integer projectId, Money amount, String clientVersion) {
        InvestOrder record = new InvestOrder();
        record.setAccId(userId);
        record.setAccName(accountName);
        record.setAmount(amount.getCent());
        record.setProId(projectId);
        record.setCreateTime(new Date());
        record.setStatus(InvestStatus.APPLYING.getValue());
        record.setAppVersion(clientVersion);
        record.setNo(SeqNoUtil.nextSeqNo(SeqNoUtil.PREFIX_FARM_INVEST));
        return record;
    }

    /**
     * 校验投资信息的合法性
     *
     * @param amount
     * @param project
     * @return
     */
    private void validateInvestInfo(double amount, Project project,Integer vipLimit) throws Exception{
        if(project.getStatus() != Project.Status.INIT.getValue()){
            throw new AppException(AppExceptionType.INVEST_ERROR_STATUS);
        }
        if (project.getVipLimit() > vipLimit) {
            throw new AppException(AppExceptionType.INVEST_VIP_LIMIT_EXCEPTION);
        }
        if (project == null) {
            throw new AppException(AppExceptionType.INVEST_PROJECT_NOT_EXISTS);
        }
        if (!project.investing()) {
            throw new AppException(AppExceptionType.INVEST_PROJECT_NOT_INVESTING);
        }
        if (project.getMin() > amount) {
            throw new AppException(AppExceptionType.INVEST_LESS_THAN_LIMIT);
        }
        if (project.getStep() != 0 && amount % project.getStep() != 0){
            throw new AppException(AppExceptionType.INVEST_LESS_THAN_LIMIT);
        }
        if (project.getLimit() < amount) {
            throw new AppException(AppExceptionType.INVEST_MORE_THAN_LIMIT);
        }
    }

    public boolean isEnoughMoney(Account user, double cost) throws Exception {
        if (user.getMoney() >= cost) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新用户数据，并同步缓存
     *
     * @param account
     * @throws Exception
     */
    public void synUpdateAccount(Account account) throws Exception {
        account.encrptAccout();
        accountDao.update(account);
        sessionCache.cacheAccount(account);
    }

    /**
     * 根据用户名查询用户
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Account queryAccountByName(String name) throws Exception {
        return accountDao.queryAccountByName(DESUtil.getEncString(name));
    }

    public boolean isRepeat(String mobile) throws Exception {
        return accountDao.isRepeat(mobile);
    }

    /**
     * 用户注册
     *
     * @param account
     * @return
     * @throws Exception
     */
    public Account saveAccount(Account account) throws Exception {
        account.setPwd(PasswordUtil.generate(account.getPwd()));
        account.setMobile(DESUtil.getEncString(account.getAccName()));
        account.setAccName(DESUtil.getEncString(account.getAccName()));
        account.setCreateTime(new Date());
        account.setInvestMoney(0l);
        account.setMoney(0l);
        account.setVip(0);
        account.setAccIncome(0l);
        account.setFrozenMoney(0l);
        account.setDeleteState(1);
        account.setPrepareStep(0);
        int id = accountDao.add(account);
        account.setId(id);
        return account;
    }

    /**
     * 用户注册检查
     *
     * @param accName
     * @return
     */
    public boolean saveCheck(String accName,String pwd) {
        try {
            Assert.isTrue(ValidateUtil.checkMobile(accName));
            Assert.isTrue(ValidateUtil.checkPwd(pwd));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 登录同步session
     *
     * @param account 数据已解密
     * @throws Exception
     */
    private String synSession(Account account) throws Exception {
        String token = JwtHolder._instance().genToken(account);
        account.setToken(token);
        sessionCache.cacheAccount(account);
        return token;
    }

    /**
     * 更新session
     *
     * @throws Exception
     */
    public void updateSession(Account session) throws Exception {
        Account account = accountDao.queryById(Account.class, session.getId());
        account.setToken(session.getToken());
        sessionCache.cacheAccount(account);
    }

    public void updateSession(Integer id) throws Exception {
        Account account = accountDao.queryById(Account.class, id);
        Account session = sessionCache.getCache(id);
        if (session != null) {
            account.setToken(session.getToken());
        }
        sessionCache.cacheAccount(account);
    }

    /**
     * 执行登录
     *
     * @param account
     * @param imei
     * @param type
     * @return
     * @throws Exception
     */
    private String excuteLogin(Account account, String imei, int type) throws Exception {
        return excuteLogin(account,imei,type,"");
    }

    private String excuteLogin(Account account, String imei, int type,String address) throws Exception {
        userLoginService.save(account, imei, type);
        account.setLastLoginTime(new Date());
        account.setLastLoginImei(imei);
        account.setLastLoginPos(address);
        account.parseAccout();
        synUpdateAccount(account);
        return synSession(account);
    }

    /**
     * 密码登录
     *
     *
     *
     * @param account
     * @param imei
     * @return
     * @throws Exception
     */
    public String excutePwdLogin(Account account, String imei) throws Exception {
        return excuteLogin(account, imei, UserLoginService.LOGIN_TYPE_NORMAL);
    }

    public String excutePwdLogin(Account account, String imei,String address) throws Exception {
        return excuteLogin(account, imei, UserLoginService.LOGIN_TYPE_NORMAL);
    }

    /**
     * 手势验证码登录
     *
     * @param account
     * @param imei
     * @return
     * @throws Exception
     */
    public String excuteLineLogin(Account account, String imei) throws Exception {
        return excuteLogin(account, imei, UserLoginService.LOGIN_TYPE_LINE);
    }

    /**
     * Token登录
     *
     * @param account
     * @param imei
     * @return
     * @throws Exception
     */
    public String excuteTokenLogin(Account account, String imei) throws Exception {
        return excuteLogin(account, imei, UserLoginService.LOGIN_TYPE_TOKEN);
    }

    /**
     * 删除缓存
     *
     * @param account
     * @throws Exception
     */
    public void logout(Account account) throws Exception {
        if (account == null) {
            logger.warn("退出失败");
            return;
        }
        sessionCache.del(Integer.toString(account.getId()));
    }

    public Map<String,Object> getAccInfo(Account account) throws Exception{
        long investMoney = billingDao.sumInvestMoney(account.getId(),InvestStatus.HOLDING);
        int couponCount = billingDao.sumCoupon(account.getId());
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("lastLoginTime",account.getLastLoginTime());
        info.put("lastLoginPos",account.getLastLoginPos());
        info.put("property",Money.centToYuan(investMoney + account.getMoney() + account.getFrozenMoney()).getAmount());//资产总值
        info.put("couponCount",couponCount);
        info.put("vip",account.getVip());
        info.put("safeLv",getSercLev(account.getPrepareStep()));
        info.put("linePwd",account.getLinePwd());
        info.put("certNo",account.getCertNo());
        info.put("mobile",account.getMobile());
        info.put("realName",account.getRealName());
        info.put("money",Money.centToYuan(account.getMoney()).getAmount());//
        info.put("hlb",account.getPoint());
        info.put("is_binding",account.getPrepareStep() == Account.PrepareInvestStep.BIND_CARD.getValue()?"已绑定":"未绑定");
        info.put("prepare_step",account.getPrepareStep() == null?0:account.getPrepareStep());
        return info;
    }

    private String getSercLev(int preprareStep){
        if (preprareStep == 0){
            return "低";
        }else if(preprareStep < 3 && preprareStep >0) {
            return "中";
        }else if(preprareStep == 3) {
            return "高";
        }
        return "低";
    }

    /**
     * 查询并转换加密数据
     * @param id
     * @return
     * @throws Exception
     */
    public Account parseQueryById(int id) throws Exception{
        Account account = queryById(id);
        Assert.notNull(account);
        account.parseAccout();
        return account;
    }

    /**
     * 查询用户投资券
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryAccCoupon(PageInfo pageInfo,Account account) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("a.acc_id", SQLOperator.equal,account.getId()));
        return accountDao.queryAccCoupon(sqlConditions,pageInfo, "order by a.create_time");
    }

    /**
     * 查询用户消息
     * @param pageInfo
     * @param account
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryMsg(PageInfo pageInfo,Account account) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("acc_id", SQLOperator.equal,account.getId()));
        SQLCondition condition = new SQLCondition("system_msg", SQLOperator.equal, SystemConstant.SYSTEM_MSG);
        condition.setConnector("or");
        sqlConditions.add(condition);
        return accountDao.queryMsg(sqlConditions,pageInfo, "order by create_time");
    }

    /**
     * 设置阅读消息
     * @param id
     * @throws Exception
     */
    public void setMsgToRead(int id) throws Exception{
        accountDao.setMsgToRead(id);
    }

    /**
     * 绑定微信
     */
    public void setOpenIdToAccount(Account account) throws Exception {
        accountDao.setOpenIdToAccount(account);
    }

    /**
     * 绑定微信
     */
    public void updatePrepareStep(Account account) throws Exception {
        accountDao.updatePrepareStep(account.getId(),account.getPrepareStep());
    }
}
