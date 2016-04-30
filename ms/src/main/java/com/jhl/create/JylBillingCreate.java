package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class JylBillingCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("billingDate",DB_TYPE_VARCHAR,"对账日期"));
        this.propertyList.add(new PropertyHolder("userId",DB_TYPE_DOUBLE,"商户号"));
        this.propertyList.add(new PropertyHolder("count",DB_TYPE_DOUBLE,"交易总笔数"));
        this.propertyList.add(new PropertyHolder("amount",DB_TYPE_DOUBLE,"交易总金额"));
        this.propertyList.add(new PropertyHolder("succCount",DB_TYPE_DOUBLE,"成功总笔数"));
        this.propertyList.add(new PropertyHolder("succAmount",DB_TYPE_DOUBLE,"成功总金额"));
        this.propertyList.add(new PropertyHolder("failCount",DB_TYPE_DOUBLE,"失败总笔数"));
        this.propertyList.add(new PropertyHolder("failAmount",DB_TYPE_DOUBLE,"失败总金额"));
    }

    @Override
    protected String getClassName() {
        return "JytBilling";
    }

    public static void main(String[] args) {
        JylBillingCreate loginCreate = new JylBillingCreate();
        loginCreate.createCode();
    }
}
