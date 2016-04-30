package com.jhl.dao.impl.rmb;

import com.jhl.dao.IAccCouponDao;
import com.jhl.db.BaseDao;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 16/2/16.
 */
@Repository
public class AccCouponDao extends BaseDao implements IAccCouponDao {

    @Override
    public PaginationResult<Map<String, Object>> queryAccCoupon(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        PaginationResult<Map<String, Object>> result = baseDao.queryForPage(SqlContainer.ACC_COUPON_PAGE, conditions, pageInfo, order);
        return result;
    }

    @Override
    public List<Map<String, Object>> queryValidAccCoupon(List<SQLCondition> conditions) throws Exception {
        return null;
    }

    @Override
    public int updateToUsed(Object... ids) throws Exception {
        return 0;
    }
}
