package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.dao.IAccCouponDao;
import com.jhl.db.SQLOperator;
import com.jhl.pojo.biz.AccCoupon;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Coupon;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.DateUtil;
import com.jhl.util.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 16/2/16.
 * 用户投资券Service
 */
@Service("accCouponService")
public class AccCouponService extends BaseService<AccCoupon> {

    private static final Logger logger = LoggerFactory.getLogger(AccCouponService.class);

    @Autowired
    IAccCouponDao accCouponDao;
    @Autowired
    CouponService couponService;

    /**
     * 保存投资券
     * @param money
     * @param recommendAcc
     * @param couponId
     * @throws Exception
     */
    public AccCoupon addAccCoupon(double money,Account recommendAcc,int couponId) throws Exception{
        AccCoupon accCoupon = new AccCoupon();
        accCoupon.setAccId(recommendAcc.getId());
        accCoupon.setCouponId(couponId);
        accCoupon.setAmount(money);
        accCoupon.setCreateTime(new Date());
        accCoupon.setDeleteState(1);
        accCoupon.setStatus(0);
        accCoupon.setValidDate(DateUtil.addDay(new Date(),30));
        save(accCoupon);
        return accCoupon;
    }

    /**
     * 抵扣投资券
     * @param investMoney
     * @param coupondId
     * @return
     * @throws Exception
     */
    public Money couponDiscount(double investMoney, String coupondId,InvestOrder record) throws Exception{
        logger.info("投资券抵扣计算开始：investMoney:{},couponId:{},order:{}",investMoney,coupondId,record.toString());
        if (Strings.isNullOrEmpty(coupondId))return new Money(investMoney);
        AccCoupon accCoupon = queryById(Integer.parseInt(coupondId));
        long now = System.currentTimeMillis();
        Coupon coupon = couponService.queryById(accCoupon.getCouponId());
        if ( accCoupon == null || coupon == null || accCoupon.getValidDate().getTime() < now ||
                accCoupon.getStatus() != 0 || !couponService.isValidate(coupon,investMoney)) new Money(investMoney);
        logger.info("投资券抵扣成功：couponId:{}",coupondId);
        setFrozen(accCoupon);
        record.setCouponId(accCoupon.getCouponId());
        return new Money(investMoney - accCoupon.getAmount());
    }

    public void setUserd(InvestOrder investOrder) throws Exception{
        if (investOrder.getCouponId() == 0)return;//未使用投资券
        AccCoupon coupon = queryById(investOrder.getCouponId());
        if (coupon == null){
            logger.warn("满标修改投资券失败:{}",investOrder.toString());
            return;
        }
        coupon.setStatus(1);
        update(coupon);
    }

    private void setFrozen(AccCoupon coupon) throws Exception {
        coupon.setStatus(2);
        update(coupon);
    }

    /**
     * 分页查询投资券
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryAccCoupon(List<SQLCondition> conditions,
                                                                PageInfo pageInfo, String order) throws Exception{
        return accCouponDao.queryAccCoupon(conditions,pageInfo,order);
    }

    /**
     * 查询选择使用的投资券
     * @param id
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> querySelCoupon(Account account,Object ... id) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        SQLCondition sqlCondition1 = new SQLCondition("id", SQLOperator.in,id);
        SQLCondition sqlCondition2 = new SQLCondition("acc_id", SQLOperator.equal,account.getId());
        sqlConditions.add(sqlCondition1);
        sqlConditions.add(sqlCondition2);
        return accCouponDao.queryValidAccCoupon(sqlConditions);
    }

    /**
     * 查询可用的投资券
     * @param account
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> querySelCoupon(Account account) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        SQLCondition sqlCondition = new SQLCondition("acc_id", SQLOperator.equal,account.getId());
        sqlConditions.add(sqlCondition);
        return accCouponDao.queryValidAccCoupon(sqlConditions);
    }

    /**
     * 更新已使用的投资券
     * @param ids
     * @return
     * @throws Exception
     */
    public int updateToUsed(Object... ids)throws Exception{
        return accCouponDao.updateToUsed(ids);
    }

    /**
     * 获得投资券
     * @param accCoupon
     */
    public void saveCoupon(AccCoupon accCoupon){

    }
}
