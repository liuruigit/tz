package com.jhl.dao.impl.rmb;

import com.jhl.common.BalanceChangeType;
import com.jhl.common.InvestStatus;
import com.jhl.dao.IBillingDao;
import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 16/2/21.
 */
@Repository(value="billingDao")
public class  BillingDao extends BaseDao implements IBillingDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long queryLastAccMoney(int month, Integer accId) {
        List<Long> money = jdbcTemplate.queryForList(BillingDaoSql.LAST_ACC_MONEY_SQL, new Object[]{month, accId}, Long.class);
        if( money == null || money.isEmpty() ) return 0;
        return money.get(0)== null ? 0L : money.get(0);
    }

    @Override
    public long sumFrozenMoney(int month, Integer accId) {
        List<Long> money = jdbcTemplate.queryForList(BillingDaoSql.SUM_FROZEN_MONEY, new Object[]{ month, accId ,  ChannelOrder.Type.CASH.getValue() ,ChannelOrder.STATUS_CREATE } , Long.class);
        if( money == null || money.isEmpty() ) return 0;
        return money.get(0)== null ? 0L : money.get(0);
    }

    @Override
    public long sumInvestMoney(int month, Integer accId) {
        String status = String.format("(%s,%s)",InvestStatus.APPLYING.getValue() , InvestStatus.HOLDING.getValue() );

        String sql = String.format(BillingDaoSql.SUM_MONTH_INVEST_MONEY, status );

        List<Long> money =  jdbcTemplate.queryForList(sql, new Object[]{ month, accId   } , Long.class);
        if( money == null || money.isEmpty() ) return 0;
        return money.get(0)== null ? 0L : money.get(0);
    }

    @Override
    public long sumIncomeMoney(int month, Integer accId) {
        List<Long> money =   jdbcTemplate.queryForList(BillingDaoSql.SUM_ACCOUNT_CHANGE_MONEY, new Object[]{ month, accId , BalanceChangeType.INVEST_INCOME.getVal()} , Long.class);
        if( money == null || money.isEmpty() ) return 0;
        return money.get(0)== null ? 0L : money.get(0);
    }

    @Override
    public long sumRechargeMoney(int month, Integer accId) {
        List<Long> money =   jdbcTemplate.queryForList(BillingDaoSql.SUM_ACCOUNT_CHANGE_MONEY, new Object[]{month, accId, BalanceChangeType.CHARGE.getVal()}, Long.class);
        if( money == null || money.isEmpty() ) return 0;
        return money.get(0)== null ? 0L : money.get(0);
    }

    @Override
    public long sumCashMoney(int month, Integer accId) {
        List<Long> money =   jdbcTemplate.queryForList(BillingDaoSql.SUM_ACCOUNT_CHANGE_MONEY, new Object[]{ month, accId , BalanceChangeType.WITHDRAW.getVal()} , Long.class);
        if( money == null || money.isEmpty() ) return 0;
        return money.get(0)== null ? 0L : money.get(0);
    }

    @Override
    public long sumInvestHoldMoney(Integer accId) throws Exception{
        try{
            return jdbcTemplate.queryForObject(BillingDaoSql.SUM_INVEST_HOLD_MONEY,Long.class,accId);
        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public long sumInvestMoney(Integer accId,InvestStatus status) throws Exception{
        try{
            return jdbcTemplate.queryForObject(BillingDaoSql.SUM_INVEST_MONEY,Long.class,accId,status.getValue());
        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Integer sumCoupon(Integer accId) throws Exception {
        Integer result =  jdbcTemplate.queryForObject(BillingDaoSql.SUM_COUPON,Integer.class,accId);
        return result == null ? 0:result;
    }

    @Override
    public Double sumCharge(Integer accId) throws Exception {
        Double result = jdbcTemplate.queryForObject(BillingDaoSql.SUM_DAY_CHARGE,Double.class,accId,
                    DateUtil.formatToday(),ChannelOrder.STATUS_CREATE,ChannelOrder.Type.CHARGE.getValue());
        return result == null ? 0:result;
    }

    @Override
    public Double sumRangeCharge(List<SQLCondition> conditions) throws Exception {
        Double result = baseDao.queryForObj(Double.class,BillingDaoSql.SUM_RANGE_CHANNEL_AMOUNT,conditions);
        return result == null ? 0:result;

    }

    @Override
    public Map<String, Object> sumPlatform() throws Exception {
        return baseDao.queryForMap(BillingDaoSql.QUERY_HOME_PAGE);
    }

    @Override
    public Map<String, Object> queryBill(Account account, String ym) throws Exception {
        try{
            return baseDao.queryForMap(BillingDaoSql.QUERY_BILL, ym,account.getId());
        }catch (EmptyResultDataAccessException e){
            return new HashMap<String, Object>();
        }catch (Exception e){
            throw e;
        }

    }

    @Override
    public PaginationResult<Map<String, Object>> queryByMonth(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        PaginationResult<Map<String, Object>> result = baseDao.queryForPage(BillingDaoSql.QUERY_ACC_CHANGE, conditions, pageInfo, order);
        return result;
    }

    @Override
    public PaginationResult<Map<String, Object>> queryAccChange(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        PaginationResult<Map<String, Object>> result = baseDao.queryForPage(BillingDaoSql.QUERY_ACC_CHANGE, conditions, pageInfo, order);
        return result;
    }

    @Override
    public PaginationResult<Map<String, Object>> queryHlb(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        PaginationResult<Map<String, Object>> result = baseDao.queryForPage(BillingDaoSql.QUERY_HLB, conditions, pageInfo, order);
        return result;
    }
}

class BillingDaoSql{
    /** 查询最后一条account_change的余额*/
    public static String LAST_ACC_MONEY_SQL = "SELECT ACC_MONEY FROM T_ACCOUNT_CHANGE WHERE YEAR(CREATE_TIME)=YEAR(NOW()) AND MONTH(CREATE_TIME) = ? AND USER_ID = ? ORDER BY CREATE_TIME DESC LIMIT 0,1 ";

    /** 根据type汇总account_change变更记录 */
    public static String SUM_ACCOUNT_CHANGE_MONEY = "SELECT SUM(AMOUNT) FROM T_ACCOUNT_CHANGE T WHERE YEAR(CREATE_TIME)=YEAR(NOW()) AND MONTH(CREATE_TIME) = ? AND USER_ID = ? AND T.TRAN_NAME = ? ";

    /** 查询channel_order汇总申请提现的被冻结金额记录 ,用于计算用户总余额*/
    public static String SUM_FROZEN_MONEY = "SELECT SUM(AMOUNT) FROM T_CHANNEL_ORDER WHERE YEAR(CREATE_TIME)=YEAR(NOW()) AND MONTH(CREATE_TIME) = ? AND USER_ID = ? AND TYPE = ? AND STATUS=? AND DELETE_STATE=1";

    /** 查询投资订单,申请中和已持有的订单,多个status需要在后面追加 */
    public static String SUM_INVEST_HOLD_MONEY = "select SUM(AMOUNT)/100 from t_invest_order i where i.ACC_ID = ? and (i.`STATUS` = 1 or i.`STATUS` = 2)";

    /** 查询投资订单,申请中的订单 */
    public static String SUM_INVEST_MONEY = "select SUM(AMOUNT) from t_invest_order i where i.ACC_ID = ? and i.`STATUS` = ?";

    /** 查询投资订单,申请中和已持有的订单,多个status需要在后面追加 */
    public static String SUM_MONTH_INVEST_MONEY = "SELECT SUM(AMOUNT) FROM T_INVEST_ORDER WHERE YEAR(SUCC_TIME)=YEAR(NOW()) AND MONTH(SUCC_TIME) = ? AND ACC_ID = ? AND DELETE_STATE=1 AND STATUS IN %s";

    /** 查询用户投资券数 */
    public static String SUM_COUPON = "select count('x') from t_acc_coupon c where c.STATUS = 0 and c.ACC_ID = ?";
    /** 查询提现或者充值的金额 */
    public static String SUM_RANGE_CHANNEL_AMOUNT = "select SUM(amount) / 100 as chargeAmount from t_channel_order c";
    /** 查询对账单 */
    public static String QUERY_BILL = "SELECT m.acc_income,m.cash,m.charge,m.money,m.property FROM t_month_billing m where m.`YEAR_MONTH` = ? AND m.ACC_ID = ? ";

    public static String QUERY_ACC_CHANGE = "SELECT CONCAT(tran_name,'-',extra_msg1) as tran_name_web,c.type,c.acc_money / 100 as money,c.amount / 100 as amount," +
            "c.create_time,c.`tran_name`,c.extra_msg1,c.extra_msg2,c.extra_msg3,c.extra_msg4,c.order_no from t_account_change c";
    /**每日充值查询*/
    public static String SUM_DAY_CHARGE = "SELECT SUM(c.amount) from t_channel_order c where c.user_id = ? " +
            "and DATE_FORMAT(c.create_time,'%Y-%m-%d') = ? AND c.`status` = ? AND c.type = ?";
    /**葫芦币查询*/
    public static String QUERY_HLB = "SELECT CREATE_TIME,`DESC`,CHANGE_AMOUNT from t_hlb_change_record ";
    public static String QUERY_HOME_PAGE = "select COUNT('x') as investAccCount,FLOOR(SUM(INVEST_MONEY) / 100) as totalInvestMoney,FLOOR(SUM(ACC_INCOME) / 100)" +
            " as totalEarn from t_account WHERE INVEST_MONEY > 0 and DELETE_STATE = 1;";



}
