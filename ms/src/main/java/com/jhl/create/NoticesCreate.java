package com.jhl.create;

/**
 * Created by apple on 16/1/24.
 */
public class NoticesCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("id",DB_TYPE_TINY_INT,"id","否" , "否"));
        this.propertyList.add(new PropertyHolder("accName",DB_TYPE_VARCHAR,"用户名","是" , "否"));
        this.propertyList.add(new PropertyHolder("pwd",DB_TYPE_VARCHAR,"密码","是" , "否"));
        this.propertyList.add(new PropertyHolder("tradePwd",DB_TYPE_VARCHAR,"交易密码","是" , "否"));
        this.propertyList.add(new PropertyHolder("linePwd",DB_TYPE_VARCHAR,"手势密码","否" , "否"));
        this.propertyList.add(new PropertyHolder("token",DB_TYPE_VARCHAR,"token","否" , "否"));
        this.propertyList.add(new PropertyHolder("updateTime",DB_TYPE_DATETIME,"更新时间","否" , "否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建时间","否" , "否"));
        this.propertyList.add(new PropertyHolder("status",DB_TYPE_TINY_INT,"账号状态","是" , "否"));
        this.propertyList.add(new PropertyHolder("deleteStatus",DB_TYPE_TINY_INT,"逻辑删除标志位：1正常0删除","否" , "否"));
        this.propertyList.add(new PropertyHolder("recommendId",DB_TYPE_TINY_INT,"推荐人","否" , "否"));
        this.propertyList.add(new PropertyHolder("mobile",DB_TYPE_VARCHAR,"电话","是" , "否"));
        this.propertyList.add(new PropertyHolder("email",DB_TYPE_VARCHAR,"邮箱","是" , "否"));
        this.propertyList.add(new PropertyHolder("certNo",DB_TYPE_VARCHAR,"身份证号","是" , "否"));
        this.propertyList.add(new PropertyHolder("realName",DB_TYPE_VARCHAR,"真实姓名","是" , "否"));
        this.propertyList.add(new PropertyHolder("prepareStep",DB_TYPE_TINY_INT,"投资准备","否" , "否"));
        this.propertyList.add(new PropertyHolder("lv",DB_TYPE_TINY_INT,"等级","否" , "否"));
        this.propertyList.add(new PropertyHolder("point",DB_TYPE_TINY_INT,"积分","否" , "否"));
        this.propertyList.add(new PropertyHolder("money",DB_TYPE_TINY_INT,"余额","否" , "否"));
        this.propertyList.add(new PropertyHolder("frozenMoney",DB_TYPE_TINY_INT,"冻结金额","否" , "否"));
        this.propertyList.add(new PropertyHolder("investMoney",DB_TYPE_TINY_INT,"投资金额","否" , "否"));
        this.propertyList.add(new PropertyHolder("digest",DB_TYPE_VARCHAR,"摘要","否" , "否"));
        this.propertyList.add(new PropertyHolder("version",DB_TYPE_TINY_INT,"版本号","否" , "否"));
        this.propertyList.add(new PropertyHolder("accIncome",DB_TYPE_TINY_INT,"累计收益","否" , "否"));
        this.propertyList.add(new PropertyHolder("vip",DB_TYPE_TINY_INT,"vip等级","否" , "否"));
    }

    @Override
    protected String getClassName() {
        return "MsAccount";
    }

    public static void main(String[] args) {
        NoticesCreate c = new NoticesCreate();
        c.createCode();
    }
}
