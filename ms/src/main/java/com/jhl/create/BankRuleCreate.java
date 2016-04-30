package com.jhl.create;

/**
 * Created by martin on 16/1/24.
 */
public class BankRuleCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("bankName",DB_TYPE_VARCHAR,"银行名称","是" , "否"));
        this.propertyList.add(new PropertyHolder("bankShortName",DB_TYPE_VARCHAR,"银行简称","是" , "否"));
        this.propertyList.add(new PropertyHolder("limit",DB_TYPE_DOUBLE,"单笔限额","是" , "否"));
        this.propertyList.add(new PropertyHolder("dayLimit",DB_TYPE_DOUBLE,"单日限额","是" , "否"));
        this.propertyList.add(new PropertyHolder("timeLimitBegin",DB_TYPE_DATETIME,"起始时间","是" , "否"));
        this.propertyList.add(new PropertyHolder("timeLimitEnd",DB_TYPE_DATETIME,"结束时间","是" , "否"));
        this.propertyList.add(new PropertyHolder("channelName",DB_TYPE_VARCHAR,"渠道名称","是" , "否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位：1正常0删除","否" , "否"));

    }

    @Override
    protected String getClassName() {
        return "BankRule";
    }

    public static void main(String[] args) {
        BankRuleCreate c = new BankRuleCreate();
        c.createCode();
    }
}
