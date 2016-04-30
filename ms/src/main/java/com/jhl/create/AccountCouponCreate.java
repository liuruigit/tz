package com.jhl.create;

/**
 * Created by apple on 16/2/26.
 */
public class AccountCouponCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("accId",DB_TYPE_DOUBLE,"用户ID","是","否"));
        this.propertyList.add(new PropertyHolder("amount",DB_TYPE_DOUBLE,"金额","是","否"));
        this.propertyList.add(new PropertyHolder("isUsed",DB_TYPE_TINY_INT,"是否使用","是","否"));
        this.propertyList.add(new PropertyHolder("couponId",DB_TYPE_TINY_INT,"投资券ID","是","否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建时间","是","否"));
        this.propertyList.add(new PropertyHolder("usedTime",DB_TYPE_DOUBLE,"使用时间","是","否"));
    }

    @Override
    protected String getClassName() {
        return "AccCoupon";
    }

    public static void main(String[] args) {
        AccountCouponCreate c = new AccountCouponCreate();
        c.createCode();
    }
}
