package com.jhl.dao;

import com.jhl.common.InvestStatus;
import com.jhl.dao.impl.rmb.SqlContainer;
import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by amoszhou on 16/1/29.
 */
@Repository("investOrderDao")
public class InvestOrderDao extends BaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询购买已经超过最小期限的订单
     * @param userId
     * @param transLimitDay
     * @return
     */
    public List<InvestOrder> queryValidTransOrder(int userId , int transLimitDay ){


        String sql = "select * from t_invest_order where acc_id = ? and DATE_ADD(create_time , INTERVAL ? DAY) < now() " +
                "and DELETE_STATE = 1 AND STATUS = ? ";

        return baseDao.query(sql , new Object []{userId , transLimitDay , InvestStatus.APPLYING.getValue() } , InvestOrder.class);
    }

    /**
     * 查询未还款的投资订单数
     * @param accId
     * @return
     * @throws Exception
     */
    public Integer countInvestOrder(Integer accId) throws Exception {
        try{
            String sql = "select count('x') from t_invest_order i where i.ACC_ID = ? and i.`STATUS` <> ?";
            return jdbcTemplate.queryForObject(sql,Integer.class,accId, InvestStatus.PAYBACK);
        }catch (NullPointerException e){
            return 0;
        }
    }

    /**
     * 更新订单状态为持有中
     * @param proId
     * @return
     * @throws Exception
     */
    public Integer statusToHolding(Integer proId) throws Exception {
        try{
            String sql = "update t_invest_order o set `STATUS` = ? where o.PRO_ID = ?";
            return baseDao.update(sql,InvestStatus.HOLDING.getValue(),proId);
        }catch (NullPointerException e){
            return 0;
        }
    }

    public Integer statusToPayback(Integer proId) throws Exception {
        try{
            String sql = "update t_invest_order o set `STATUS` = ? where o.PRO_ID = ?";
            return baseDao.update(sql,InvestStatus.PAYBACK.getValue(),proId);
        }catch (NullPointerException e){
            return 0;
        }
    }

    public Integer statusToSETTLE(Integer proId) throws Exception {
        try{
            String sql = "update t_invest_order o set `STATUS` = ? where o.PRO_ID = ?";
            return baseDao.update(sql,InvestStatus.SETTLE.getValue(),proId);
        }catch (NullPointerException e){
            return 0;
        }
    }

    /**
     * 查询投资订单
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryInvestRecord(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        return baseDao.queryForPage(SqlContainer.QUERY_INVEST_RECORD, conditions, pageInfo, order);
    }

}
