package com.jhl.service;

import com.jhl.common.BalanceChangeType;
import com.jhl.common.InvestStatus;
import com.jhl.dao.IProjectDao;
import com.jhl.dao.InvestOrderDao;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojo.biz.Project;
import com.jhl.util.Money;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/27.
 * 标的核心业务Service
 */
@Service
public class TransactionService {
    private static Logger logger = LoggerFactory.getLogger(TransactionService.class);
    @Autowired
    InvestOrderService investOrderService;
    @Autowired
    InviteRewardConfigService inviteRewardConfigService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccCouponService accCouponService;
    @Autowired
    IProjectDao projectDao;
    @Autowired
    InvestOrderDao investOrderDao;

    /**
     * 财务确认满标
     * @param project
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean ensure(Project project) throws Exception{
        /**
         * 检查标的金额是否和投资总额一致
         */
        logger.info("标的融资成功，平台数据更新开始:{}",project.toString());
        if (projectDao.checkInvest(project.getId())){
            List<InvestOrder> investOrders = investOrderService.
                    getInvestOrderByProjectId(project,InvestStatus.APPLYING);
            for(InvestOrder investOrder : investOrders){
                Account account = accountService.queryById(investOrder.getAccId());
                /**
                 * 修改投资券状态为已使用
                 */
                accCouponService.setUserd(investOrder);

                if(account == null || account.getDeleteState() == 0){
                    logger.error("投资订单记录用户不存在或已删除");
                }

                /**
                 * 发放邀请奖励
                 */
                inviteRewardConfigService.reWardRecommendAcc(account,investOrder.getAmount() / 100);

                /**
                 * 更新用户投资金额
                 */
                logger.info("用户持有标的成功，更新投资金额:{}",account.toString());
                accountService.updateUserBalance(account.getId(), Money.centToYuan(investOrder.getAmount()),
                        BalanceChangeType.INVEST,investOrder.getNo());
            }
            /**
             * 更新标的状态为等待结算
             */
            projectDao.updateStatus(project.getId(), Project.Status.WAIT_BALANCED);
            /**
             * 更新投资订单为持有中
             */
            investOrderDao.statusToHolding(project.getId());
            logger.info("标的融资成功，平台数据更新完毕.....");
            return true;
        }else{
            logger.info("标的融资，平台数据更新开始:{}",project.toString());
            return false;
        }
    }

    /**
     * 标的还款
     * @param project
     */
    @Transactional
    public void settleProject(Project project) throws Exception{
        if (project == null) {
            logger.error(SessionUtil.getNo() + "标的分润失败，标的为空");
            return;
        }

        Map<String,Object> name = projectDao.queryProName(project.getId());
        logger.info("标的还款开始,{}",project.toString());
        /**
         * 获取收益金额
         */
        double earn = project.getFinalAmount() - project.getAmount();
        logger.info("标的收益:{}",earn);
        List<InvestOrder> investOrders = investOrderService.getInvestOrderByProjectId(project,
                InvestStatus.APPLYING);
        for (InvestOrder investOrder : investOrders) {
            Long investAmount = investOrder.getAmount();
            /**
             * 用户投资获得之收益 = 用户投资/标的融资总额 * 标的收益(元)
             */
            double userEarn = Math.floor(earn * (investAmount / new Money(project.getAmount()).getCent()));
            logger.info("用户：{}收益:{}",investOrder.getAccId(),userEarn);
            /**
             * 执行用户还款
             */
            accountService.updateUserBalance(investOrder.getAccId(),Money.centToYuan(investOrder.getAmount()),
                    new Money(userEarn), BalanceChangeType.Settlement,investOrder.getNo(),
                    name.get("NAME") == null?project.getNo():name.get("NAME").toString(),
                    project.getNo());
        }
        /**
         * 更新投资订单为已还款
         */
        investOrderDao.statusToPayback(project.getId());
        /**
         * 更新标的为已还款
         */
        projectDao.updateStatus(project.getId(),Project.Status.REPAY);
        logger.info("标的还款完成！");

    }

    /**
     *  合作方确认标的清算完成
     * @param project
     */
    @Transactional
    public void preSettle(Project project) throws Exception{

        investOrderDao.statusToSETTLE(project.getId());

        projectDao.preSettle(project,Project.Status.BALANCED);

    }

//    /**
//     * 计算实际年化收益 = 获取收益金额 / 本金
//     * @return
//     * @throws Exception
//     */
//    private double calActualPerYearRate() throws Exception{
//
//    }
}
