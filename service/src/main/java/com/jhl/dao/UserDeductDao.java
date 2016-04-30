package com.jhl.dao;

import com.jhl.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by vic wu on 2015/8/19.
 */
@Repository(value = "userDeductDao")
public class UserDeductDao extends BaseDao {

//    public UserDeduct queryUserPactByOutBizNo(String outBizNo) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("outBizNo", outBizNo);
//        return baseDao.queryForObject(UserDeduct.class, map);
//    }
//
//    public ChargeInfo queryChargeInfoByOrderNo(String orderNo) throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("orderNo", orderNo);
//        return baseDao.queryForObject(ChargeInfo.class, map);
//    }
}
