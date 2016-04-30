package com.jhl.dao.impl.rmb;

import com.jhl.dao.IChannelOrderDao;
import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.ChannelOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/17.
 */
@Repository
public class ChannelOrderDao extends BaseDao implements IChannelOrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ChannelOrder queryChannelOrderByTransflow(String transflow) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tran_flow", transflow);
        return baseDao.queryForObject(ChannelOrder.class, map);
    }

    @Override
    public Integer queryChannelOrder(String transflow) throws Exception {
        try{
            String sql = "select count('x') from t_channel_order o where o.tran_flow = ?";
            return jdbcTemplate.queryForObject(sql,Integer.class,transflow);
        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Integer countUnfinishedChargeOrder(Integer accId) throws Exception {
        try{
            String sql = "select count('x') from t_channel_order o where o.user_id = ? and o.`status` < ? and o.type = ?";
            return jdbcTemplate.queryForObject(sql,Integer.class,accId,ChannelOrder.STATUS_TRANSACTION_SUCCESS,ChannelOrder.Type.CHARGE.getValue());
        }catch (NullPointerException e){
            return 0;
        }
    }

    @Override
    public Integer countUnfinishedCashOrder(Integer accId) throws Exception {
        try{
            String sql = "select count('x') from t_channel_order o where o.user_id = ? and o.`status` < ? and o.type = ?";
            return jdbcTemplate.queryForObject(sql,Integer.class,accId,ChannelOrder.STATUS_TRANSACTION_SUCCESS,ChannelOrder.Type.CASH.getValue());
        }catch (NullPointerException e){
            return 0;
        }
    }

}
