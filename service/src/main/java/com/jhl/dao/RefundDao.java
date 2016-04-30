package com.jhl.dao;

import com.jhl.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by hallywu on 15/10/11.
 */
@Repository
public class RefundDao extends BaseDao {

    public double sumRefundMoney(int financingId) throws Exception{
        String sql = "select sum(money) from t_refund_order where status = 2 and financing_id = ?";
        return baseDao.queryForDouble(sql,financingId);
    }

}
