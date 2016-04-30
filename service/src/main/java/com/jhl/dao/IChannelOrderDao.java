package com.jhl.dao;

import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/17.
 */
public interface IChannelOrderDao {

    /**
     * 查询充值订单数量
     * @param transflow
     * @return
     * @throws Exception
     */
    public Integer queryChannelOrder(String transflow) throws Exception;
    /**
     * 根据合作资管渠道订单号查询订单
     * @param transflow
     * @return
     * @throws Exception
     */
    public ChannelOrder queryChannelOrderByTransflow(String transflow) throws Exception;

    /**
     * 统计进行中的提现或者充值记录
     * @param accId
     * @return
     * @throws Exception
     */
    public Integer countUnfinishedChargeOrder(Integer accId) throws Exception ;
    public Integer countUnfinishedCashOrder(Integer accId) throws Exception ;



}
