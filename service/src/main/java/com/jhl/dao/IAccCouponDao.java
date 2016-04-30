package com.jhl.dao;

import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;

import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 16/2/16.
 */
public interface IAccCouponDao {

    /**
     * 按分页查询用户所有投资券
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryAccCoupon(List<SQLCondition> conditions,
                                                                PageInfo pageInfo, String order) throws Exception;

    /**
     * 查询用户投资券
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryValidAccCoupon(List<SQLCondition> conditions) throws Exception;

    /**
     * 更新投资券
     * @param ids
     * @return
     * @throws Exception
     */
    public int updateToUsed(Object... ids)throws Exception;
}
