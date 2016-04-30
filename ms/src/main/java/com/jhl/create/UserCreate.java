package com.jhl.create;

/**
 * Created by Administrator on 2016/1/13.
 */
public class UserCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("accName",DB_TYPE_VARCHAR,"账号","是","否"));
        this.propertyList.add(new PropertyHolder("pwd",DB_TYPE_VARCHAR,"密码","是","否"));
        this.propertyList.add(new PropertyHolder("tradePwd",DB_TYPE_VARCHAR,"交易密码","是","否"));
        this.propertyList.add(new PropertyHolder("linePwd",DB_TYPE_VARCHAR,"手势密码"));
        this.propertyList.add(new PropertyHolder("token",DB_TYPE_VARCHAR,"token"));
        this.propertyList.add(new PropertyHolder("mobile",DB_TYPE_VARCHAR,"手机号","是","否"));
        this.propertyList.add(new PropertyHolder("email",DB_TYPE_VARCHAR,"邮箱","是","否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建时间"));
        this.propertyList.add(new PropertyHolder("lastLoginTime",DB_TYPE_DATETIME,"上次登录时间"));
        this.propertyList.add(new PropertyHolder("status",DB_TYPE_TINY_INT,"状态"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"1正常0删除"));
        this.propertyList.add(new PropertyHolder("certNo",DB_TYPE_VARCHAR,"身份证号","是","否"));
        this.propertyList.add(new PropertyHolder("realName",DB_TYPE_VARCHAR,"真实姓名","是","否"));
        this.propertyList.add(new PropertyHolder("isRealName",DB_TYPE_TINY_INT,"是否实名","是","否"));
        this.propertyList.add(new PropertyHolder("money",DB_TYPE_DOUBLE,"余额","是","否"));
        this.propertyList.add(new PropertyHolder("freezeMoney",DB_TYPE_DOUBLE,"冻结金额","是","否"));
        this.propertyList.add(new PropertyHolder("lastLoginImei",DB_TYPE_VARCHAR,"上次登录设备"));
    }

    @Override
    protected String getClassName() {
        return "Client";
    }

    public static void main(String[] args) {
        UserCreate userCreate = new UserCreate();
        userCreate.createCode();
    }
}
