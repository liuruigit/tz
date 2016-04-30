package com.jhl.create;

/**
 * Created by Administrator on 2016/1/23.
 */
public class ConfigCreate extends BaseCreate{

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("key",DB_TYPE_VARCHAR,"配置KEY","是","否"));
        this.propertyList.add(new PropertyHolder("value",DB_TYPE_VARCHAR,"配置value","是","否"));
    }

    @Override
    protected String getClassName() {
        return "Config";
    }

    public static void main(String[] args) {
        ConfigCreate loginCreate = new ConfigCreate();
        loginCreate.createCode();
    }

}
