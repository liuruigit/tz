package com.jhl.dao;

import com.jhl.common.InvestStatus;
import com.jhl.pojo.biz.Account;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;

import java.util.List;
import java.util.Map;

/**
 * Created by martin on 16/2/21.
 */
public interface IBillingDao {

    /**
     * 查询首页数据
     * @return
     * @throws Exception
     */
    public Map<String, Object> sumPlatform() throws Exception;

    /**
     * 查询葫芦币明细
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryHlb(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception;

    /**
     * 统计时间段内的充值或者提现
     * @param conditions
     * @return
     * @throws Exception
     */
    public Double sumRangeCharge(List<SQLCondition> conditions) throws Exception;

    /**
     * 根据月份查询
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryByMonth(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception;

    /**
     * 查询用户当日充值金额
     * @param accId
     * @return
     * @throws Exception
     */
    public Double sumCharge(Integer accId) throws Exception;

    /**
     * 查询交易记录
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryAccChange(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception;

    /**
     * 查询用户某月份最后的余额记录
     * @param month 统计月份
     * @param accId 用户id
     * @return 余额
     */
    public long queryLastAccMoney(int month ,Integer accId);

    /**
     * 查询当月申请提现总金额
     * @param month
     * @param accId
     * @return
     */
    public long sumFrozenMoney(int month , Integer accId);

    /**
     * 查询当月持有中和申请中的投资金额
     * @param month
     * @param accId
     * @return
     */
    public long sumInvestMoney(int month , Integer accId);

    /**
     * 查询当月t_account_change中收益总记录
     * @param month
     * @param accId
     * @return
     */
    public long sumIncomeMoney(int month , Integer accId);

    /**
     * 查询当月t_account_change中充值总记录
     * @param month
     * @param accId
     * @return
     */
    public long sumRechargeMoney(int month , Integer accId );

    /**
     * 查询当月t_account_change中提现总记录
     * @param month
     * @param accId
     * @return
     */
    public long sumCashMoney(int month , Integer accId );

    /**
     * 查询用户投资中的资金(元)
     * @param accId
     * @return
     */
    public long sumInvestMoney(Integer accId,InvestStatus status) throws Exception;

    /**
     * 查询用户投资中的资金(元)
     * @param accId
     * @return
     */
    public long sumInvestHoldMoney(Integer accId) throws Exception;

    /**
     * 查询用户的投资券
     * @param accId
     * @return
     * @throws Exception
     */
    public Integer sumCoupon(Integer accId) throws Exception;

    /**
     * 查询对账单
     * @param account
     * @param ym
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryBill(Account account, String ym) throws Exception;
}
