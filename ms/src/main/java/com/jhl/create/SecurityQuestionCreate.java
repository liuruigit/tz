package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class SecurityQuestionCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("accId",DB_TYPE_TINY_INT,"用户"));
        this.propertyList.add(new PropertyHolder("question",DB_TYPE_VARCHAR,"问题"));
        this.propertyList.add(new PropertyHolder("answer",DB_TYPE_VARCHAR,"答案"));
        this.propertyList.add(new PropertyHolder("updateTime",DB_TYPE_DATETIME,"更新时间"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建时间"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位"));
    }

    @Override
    protected String getClassName() {
        return "SecurityQuestion";
    }

    public static void main(String[] args) {
        SecurityQuestionCreate loginCreate = new SecurityQuestionCreate();
        loginCreate.createCode();
    }
}
