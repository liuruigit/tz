package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class LoginCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("tranType",DB_TYPE_VARCHAR,"交易类型"));
        this.propertyList.add(new PropertyHolder("tranDate",DB_TYPE_DATETIME,"商户交易时间"));
        this.propertyList.add(new PropertyHolder("tranDated",DB_TYPE_DATETIME,"交易完成时间"));
        this.propertyList.add(new PropertyHolder("orderId",DB_TYPE_VARCHAR,"商户订单号"));
        this.propertyList.add(new PropertyHolder("num",DB_TYPE_VARCHAR,"批次号"));
        this.propertyList.add(new PropertyHolder("repOrderId",DB_TYPE_VARCHAR,"代收付平台订单号"));
        this.propertyList.add(new PropertyHolder("tranAmout",DB_TYPE_DOUBLE,"交易金额"));
        this.propertyList.add(new PropertyHolder("currency",DB_TYPE_VARCHAR,"币种"));
        this.propertyList.add(new PropertyHolder("bankNo",DB_TYPE_VARCHAR,"客户交易银行账号"));
        this.propertyList.add(new PropertyHolder("bankName",DB_TYPE_VARCHAR,"客户交易银行账户名称"));
        this.propertyList.add(new PropertyHolder("userFicNo",DB_TYPE_VARCHAR,"商户交易虚拟账号"));
        this.propertyList.add(new PropertyHolder("resultNo",DB_TYPE_VARCHAR,"交易结果码"));
        this.propertyList.add(new PropertyHolder("resultDesc",DB_TYPE_VARCHAR,"交易结果描述"));
        this.propertyList.add(new PropertyHolder("pound",DB_TYPE_DOUBLE,"手续费"));
    }

    @Override
    protected String getClassName() {
        return "TranDetails";
    }

    public static void main(String[] args) {
        LoginCreate loginCreate = new LoginCreate();
        loginCreate.createCode();
    }
}
