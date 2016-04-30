package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class CouponCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("desc",DB_TYPE_VARCHAR,"id","描述" , "否"));
        this.propertyList.add(new PropertyHolder("name",DB_TYPE_VARCHAR,"标题","投资券名称" , "否"));
        this.propertyList.add(new PropertyHolder("min",DB_TYPE_VARCHAR,"金额下限","是" , "否"));
        this.propertyList.add(new PropertyHolder("beginDate",DB_TYPE_DATETIME,"开始日期","是" , "否"));
        this.propertyList.add(new PropertyHolder("endDate",DB_TYPE_DATETIME,"截止日期","是" , "否"));
        this.propertyList.add(new PropertyHolder("tag",DB_TYPE_TINY_INT,"适用范围","是" , "否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位：1正常0删除","是" , "否"));
        this.propertyList.add(new PropertyHolder("create_time",DB_TYPE_DATETIME,"逻辑删除标志位：1正常0删除","是" , "否"));
    }

    @Override
    protected String getClassName() {
        return "Coupon";
    }

    public static void main(String[] args) {
        CouponCreate loginCreate = new CouponCreate();
        loginCreate.createCode();
    }
}
