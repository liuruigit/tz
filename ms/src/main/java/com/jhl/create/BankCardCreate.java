package com.jhl.create;

/**
 * Created by martin on 16/1/24.
 */
public class BankCardCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("bankName",DB_TYPE_VARCHAR,"银行名称","是" , "否"));
        this.propertyList.add(new PropertyHolder("name",DB_TYPE_VARCHAR,"名称","是" , "否"));
        this.propertyList.add(new PropertyHolder("userId",DB_TYPE_TINY_INT,"用户ID","是" , "否"));
        this.propertyList.add(new PropertyHolder("bankCardNo",DB_TYPE_VARCHAR,"银行卡号","是" , "否"));
        this.propertyList.add(new PropertyHolder("pro",DB_TYPE_VARCHAR,"省份","是" , "否"));
        this.propertyList.add(new PropertyHolder("city",DB_TYPE_VARCHAR,"城市","是" , "否"));
        this.propertyList.add(new PropertyHolder("branch",DB_TYPE_VARCHAR,"支行","是" , "否"));
        this.propertyList.add(new PropertyHolder("bankCode",DB_TYPE_VARCHAR,"code","是" , "否"));
        this.propertyList.add(new PropertyHolder("isDefault",DB_TYPE_TINY_INT,"是否默认卡","是" , "否"));
        this.propertyList.add(new PropertyHolder("prcptcd",DB_TYPE_VARCHAR,"协议号","是" , "否"));
        this.propertyList.add(new PropertyHolder("noAgree",DB_TYPE_VARCHAR,"签约协议号","是" , "否"));
        this.propertyList.add(new PropertyHolder("mobile",DB_TYPE_VARCHAR,"绑定的手机号","是" , "否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建时间","是" , "否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位：1正常0删除","否" , "否"));

    }

    @Override
    protected String getClassName() {
        return "Bankcard";
    }

    public static void main(String[] args) {
        BankCardCreate c = new BankCardCreate();
        c.createCode();
    }
}
