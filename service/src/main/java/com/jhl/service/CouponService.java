package com.jhl.service;

import com.jhl.pojo.biz.Coupon;
import com.jhl.util.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/29.
 */
@Service("couponService")
public class CouponService extends BaseService<Coupon>{

    public boolean isValidate(Coupon coupon, double investMoney) {
        if (coupon == null || investMoney == 0) return false;
        long now = new Date().getTime();
        return (coupon.getBeginDate().getTime() <= now) && (now <= coupon.getEndDate().getTime())
                && coupon.getMin() <= investMoney;
    }

}
