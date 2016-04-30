package com.jhl.create;

/**
 * Created by Administrator on 2016/1/23.
 */
public class InviteRewardConfig extends BaseCreate{

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("days",DB_TYPE_VARCHAR,"期限","是","否"));
        this.propertyList.add(new PropertyHolder("rangeStart",DB_TYPE_VARCHAR,"投资金额（起）","是","否"));
        this.propertyList.add(new PropertyHolder("rangeEnd",DB_TYPE_VARCHAR,"投资金额（止）","是","否"));
        this.propertyList.add(new PropertyHolder("perc",DB_TYPE_VARCHAR,"配置value","是","否"));
        this.propertyList.add(new PropertyHolder("isOpen",DB_TYPE_VARCHAR,"是否开启","是","否"));
    }

    @Override
    protected String getClassName() {
        return "InviteRewardConfig";
    }

    public static void main(String[] args) {
        InviteRewardConfig loginCreate = new InviteRewardConfig();
        loginCreate.createCode();
    }

}
