package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class AccChangeCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("userId",DB_TYPE_VARCHAR,"用户ID"));
        this.propertyList.add(new PropertyHolder("amount",DB_TYPE_DOUBLE,"交易金额"));
        this.propertyList.add(new PropertyHolder("accMoney",DB_TYPE_DOUBLE,"用户余额"));
        this.propertyList.add(new PropertyHolder("orderNo",DB_TYPE_VARCHAR,"订单号"));
        this.propertyList.add(new PropertyHolder("tranName",DB_TYPE_VARCHAR,"交易名称"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"交易时间"));
        this.propertyList.add(new PropertyHolder("extraMsg1",DB_TYPE_VARCHAR," 补充信息（1）"));
        this.propertyList.add(new PropertyHolder("extraMsg2",DB_TYPE_VARCHAR,"补充信息（2）"));
        this.propertyList.add(new PropertyHolder("extraMsg3",DB_TYPE_VARCHAR,"补充信息（3）"));
        this.propertyList.add(new PropertyHolder("extraMsg4",DB_TYPE_VARCHAR,"补充信息（4）"));
    }

    @Override
    protected String getClassName() {
        return "AccountChange";
    }

    public static void main(String[] args) {
        AccChangeCreate loginCreate = new AccChangeCreate();
        loginCreate.createCode();
    }
}
