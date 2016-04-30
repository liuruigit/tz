package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.common.InvestStatus;
import com.jhl.dao.impl.rmb.BillingDao;
import com.jhl.db.SQLOperator;
import com.jhl.db.SQLUtils;
import com.jhl.dto.BillingDto;
import com.jhl.dto.ChannelOrderDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.pojo.biz.MonthBilling;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by martin on 16/2/21.
 * billing服务，每月第一日的03:00统计用户上月的对账单数据，
 * 并插入月对账记录表（t_month_billing）中，对账单页面按月查询t_month_billing的数据即可
 */
@Service
public class BillingService extends BaseService{

    @Autowired
    BillingDao billingDao;
    @Autowired
    AccountService accountService;

    /**
     * 查询交易记录
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryAccChange(PageInfo pageInfo, Account account, BillingDto dto) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        if(!Strings.isNullOrEmpty(dto.getDesc())){
            sqlConditions.add(new SQLCondition("tran_name", SQLOperator.equal, dto.getDesc()));
        }
        if(buildCondition(dto) != null){
            sqlConditions.add(buildCondition(dto));
        }
        return billingDao.queryAccChange(sqlConditions, pageInfo, "order by c.create_time desc");
    }

    private SQLCondition buildCondition(BillingDto dto){
        if (!Strings.isNullOrEmpty(dto.getDateBegin()) && Strings.isNullOrEmpty(dto.getDateEnd())){
            return new SQLCondition("c.create_time", SQLOperator.greatEqualThan,dto.getDateBegin());
        }
        if (Strings.isNullOrEmpty(dto.getDateBegin()) && !Strings.isNullOrEmpty(dto.getDateEnd())){
            return new SQLCondition("c.create_time", SQLOperator.lessEqualThan,dto.getDateEnd());
        }
        if (!Strings.isNullOrEmpty(dto.getDateBegin()) && !Strings.isNullOrEmpty(dto.getDateEnd())){
            return new SQLCondition("c.create_time", SQLOperator.between,new Object[]{dto.getDateBegin(),dto.getDateEnd()});
        }
        return null;
    }

    /**
     * 对某个用户结算
     * @param month
     * @param accId
     */
    public void doBilling(int month , Integer accId) throws Exception {
        MonthBilling bill = new MonthBilling();
        String date = Calendar.YEAR+""+month;
        bill.setAccId(accId);
        bill.setCreateTime(new Date());
        bill.setAccIncome(sumIncomeMoney(month , accId ) * 100);
        bill.setCash(sumCashMoney( month , accId ) * 100);
        bill.setCharge( sumRechargeMoney( month , accId ) * 100);
        bill.setMoney( queryLastAccMoney( month , accId ) * 100);
        bill.setYearMonth(date);
        calculateProperty( bill , month , accId );
        billingDao.add(bill);
    }

    /**
     * 查询用户某月份最后的余额记录
     * @param month 统计月份
     * @param accId 用户id
     * @return 余额
     */
    public Long queryLastAccMoney(int month ,Integer accId){
        return billingDao.queryLastAccMoney( month , accId );
    }

    /**
     * 查询对账单
     * @param account
     * @param ym
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryBill(Account account,String ym) throws Exception{
        Map<String, Object> map = billingDao.queryBill( account , ym );
        formatPara(map);
        map.put("totalEarn", Money.centToYuan(account.getAccIncome()).getAmount());
        map.put("totalInvest",Money.centToYuan(account.getInvestMoney()).getAmount());
        return map;
    }

    private Map<String, Object> formatPara(Map<String, Object> map){
        if (map != null && map.size() == 0){
            map.put("acc_income", "0.00");
            map.put("cash", "0.00");
            map.put("money", "0.00");
            map.put("charge", "0.00");
            map.put("property", "0.00");
        }
        return map;
    }


    /**
     * 查询当月申请提现总金额
     * @param month
     * @param accId
     * @return
     */
    public Long sumFrozenMoney(int month , Integer accId){
        return billingDao.sumFrozenMoney( month , accId );
    }

    /**
     * 查询当月持有中和申请中的投资金额
     * @param month
     * @param accId
     * @return
     */
    public Long sumInvestMoney(int month , Integer accId){
        return billingDao.sumInvestMoney( month , accId );
    }

    /**
     * 查询当月t_account_change中收益总记录
     * @param month
     * @param accId
     * @return
     */
    public Long sumIncomeMoney(int month , Integer accId){
        return billingDao.sumIncomeMoney(month, accId);
    }

    /**
     * 查询当月t_account_change中充值总记录
     * @param month
     * @param accId
     * @return
     */
    public Long sumRechargeMoney(int month , Integer accId ){
        return billingDao.sumRechargeMoney( month , accId );
    }

    /**
     * 查询当月t_account_change中提现总记录
     * @param month
     * @param accId
     * @return
     */
    public Long sumCashMoney(int month , Integer accId ){
        return billingDao.sumCashMoney( month , accId );
    }

    /**
     * 查询用户的单日充值金额
     * @param accId
     * @return
     * @throws Exception
     */
    public Double sumCharge(Integer accId ) throws Exception{
        return billingDao.sumCharge( accId );
    }

    /**
     * 计算总资产
     * @param bill
     * @return
     */
    public void calculateProperty(MonthBilling bill , int month , Integer accId){
        Money frozenMoney = new Money(sumFrozenMoney( month , accId ));
        Money investMoney = new Money (sumInvestMoney( month , accId ));
        Money money = new Money ( bill.getMoney() );
        bill.setProperty( money.add(frozenMoney).add( investMoney).getCent());
    }

    /**
     * 查询资产详情
     * @param account
     * @return
     * @throws Exception
     */
    public Map<String,Object> propertyDetail(Account account) throws Exception{
        long investMoney = billingDao.sumInvestMoney(account.getId(), InvestStatus.HOLDING);
        long applying = billingDao.sumInvestMoney(account.getId(), InvestStatus.APPLYING);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("property",Money.centToYuan(account.getFrozenMoney() + account.getMoney() + investMoney).getAmount());
        map.put("investMoney",Money.centToYuan(investMoney).getAmount());
        map.put("frozenMoney",Money.centToYuan(account.getFrozenMoney()).getAmount());
        map.put("applying",Money.centToYuan(applying).getAmount());
        map.put("cashing",Money.centToYuan(account.getFrozenMoney() - applying).getAmount());
        map.put("money",Money.centToYuan(account.getMoney()).getAmount());
        return map;
    }

    /**
     * 查询提现或充值记录,按月查询
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryByMonth_channel(PageInfo pageInfo, Account account, ChannelOrderDto dto) throws Exception{
        List<SQLCondition> conditions = new ArrayList<SQLCondition>();
        conditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        if (Strings.isNullOrEmpty(dto.getType()) && Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("c.tran_name", SQLOperator.in,new Object[]{"提现","充值"}));//// TODO: 2016/3/19 写死需替换
        }else if (!Strings.isNullOrEmpty(dto.getType()) && Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("c.tran_name", SQLOperator.equal,dto.getType()));
        }else if (Strings.isNullOrEmpty(dto.getType()) && !Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("c.tran_name", SQLOperator.in,new Object[]{"提现","充值"}));//// TODO: 2016/3/19 写死需替换
            conditions.add(new SQLCondition("date_format(c.create_time,'%Y-%m')", SQLOperator.equal,dto.getDate()));
        }else if (!Strings.isNullOrEmpty(dto.getType()) && !Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("c.tran_name", SQLOperator.equal,dto.getType()));
            conditions.add(new SQLCondition("date_format(c.create_time,'%Y-%m')", SQLOperator.equal,dto.getDate()));
        }
        return billingDao.queryByMonth(conditions,pageInfo, SQLUtils.buildOrderStr(dto.getOrderName(),dto.getOrder()));
    }

    /**
     * 快捷日期查询提现充值
     * @param pageInfo
     * @param account
     * @param dto
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryByRang_channel(PageInfo pageInfo, Account account, ChannelOrderDto dto) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        if (Strings.isNullOrEmpty(dto.getType())) {
            sqlConditions.add(new SQLCondition("c.tran_name", SQLOperator.in,new Object[]{"提现","充值"}));//// TODO: 2016/3/19 写死需替换
        }else{
            sqlConditions.add(new SQLCondition("c.tran_name", SQLOperator.equal,dto.getType()));
        }
        sqlConditions.add(SQLUtils.buildSqlConditionByDate(Integer.parseInt(dto.getRange())));
        return billingDao.queryAccChange(sqlConditions, pageInfo, SQLUtils.buildOrderStr(dto.getOrderName(),dto.getOrder()));
    }

    /**
     * 查询交易记录
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryByMonth(PageInfo pageInfo, Account account, ChannelOrderDto dto) throws Exception{
        List<SQLCondition> conditions = new ArrayList<SQLCondition>();
        conditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        if (!Strings.isNullOrEmpty(dto.getType()) && Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("c.tran_name", SQLOperator.equal,dto.getType()));
        }else if (Strings.isNullOrEmpty(dto.getType()) && !Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("date_format(c.create_time,'%Y-%m')", SQLOperator.equal,dto.getDate()));
        }else if (!Strings.isNullOrEmpty(dto.getType()) && !Strings.isNullOrEmpty(dto.getDate())) {
            conditions.add(new SQLCondition("c.tran_name", SQLOperator.equal,dto.getType()));
            conditions.add(new SQLCondition("date_format(c.create_time,'%Y-%m')", SQLOperator.equal,dto.getDate()));
        }
        return billingDao.queryByMonth(conditions,pageInfo, SQLUtils.buildOrderStr(dto.getOrderName(),dto.getOrder()));
    }

    /**
     * 查询交易记录
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryHlb(PageInfo pageInfo, Account account) throws Exception{
        List<SQLCondition> conditions = new ArrayList<SQLCondition>();
        conditions.add(new SQLCondition("acc_id", SQLOperator.equal,account.getId()));
        return billingDao.queryHlb(conditions,pageInfo,"order by create_time desc");
    }

    /**
     * 按日期查询
     * @param pageInfo
     * @param account
     * @param dto
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryByRange(PageInfo pageInfo, Account account, ChannelOrderDto dto) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        if (!Strings.isNullOrEmpty(dto.getType())) {
            sqlConditions.add(new SQLCondition("c.tran_name", SQLOperator.equal,dto.getType()));
        }
        sqlConditions.add(SQLUtils.buildSqlConditionByDate(Integer.parseInt(dto.getRange())));
        return billingDao.queryAccChange(sqlConditions, pageInfo, SQLUtils.buildOrderStr(dto.getOrderName(),dto.getOrder()));
    }

    private double sumChannelAmountByMonth(Account account,ChannelOrderDto dto,int type,int status) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        sqlConditions.add(new SQLCondition("status", SQLOperator.equal,status));
        sqlConditions.add(new SQLCondition("type", SQLOperator.equal,type));
        sqlConditions.add(new SQLCondition("date_format(c.create_time,'%Y-%m')", SQLOperator.equal,dto.getDate()));
        return billingDao.sumRangeCharge(sqlConditions);
    }

    private double sumChannelAmountByRange(Account account,ChannelOrderDto dto,int type,int status) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("userId", SQLOperator.equal,account.getId()));
        sqlConditions.add(new SQLCondition("status", SQLOperator.equal,status));
        sqlConditions.add(new SQLCondition("type", SQLOperator.equal,type));
        sqlConditions.add(SQLUtils.buildSqlConditionByDate(Integer.parseInt(dto.getRange())));
        return billingDao.sumRangeCharge(sqlConditions);
    }

    public double sumChargeAmountByRange(Account account,ChannelOrderDto dto) throws Exception{
        return sumChannelAmountByRange(account,dto, ChannelOrder.Type.CHARGE.getValue(),ChannelOrder.STATUS_SUCCESS);
    }

    public double sumCashAmountByRange(Account account,ChannelOrderDto dto) throws Exception{
        return sumChannelAmountByRange(account,dto, ChannelOrder.Type.CASH.getValue(),ChannelOrder.STATUS_TRANSACTION_SUCCESS);
    }

    public double sumChargeAmountByMonth(Account account,ChannelOrderDto dto) throws Exception{
        return sumChannelAmountByMonth(account,dto, ChannelOrder.Type.CHARGE.getValue(),ChannelOrder.STATUS_SUCCESS);
    }

    public double sumCashAmountByMonth(Account account,ChannelOrderDto dto) throws Exception{
        return sumChannelAmountByMonth(account,dto, ChannelOrder.Type.CASH.getValue(),ChannelOrder.STATUS_TRANSACTION_SUCCESS);
    }

    public Map<String,Object> sumPlatform() throws Exception{
        return billingDao.sumPlatform();
    }
}
