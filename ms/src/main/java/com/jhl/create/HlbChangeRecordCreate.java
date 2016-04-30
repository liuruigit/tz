package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class HlbChangeRecordCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("accId",DB_TYPE_VARCHAR,"用户ID"));
        this.propertyList.add(new PropertyHolder("amount",DB_TYPE_DOUBLE,"葫芦币数量"));
        this.propertyList.add(new PropertyHolder("changeAmount",DB_TYPE_DOUBLE,"葫芦币变更数量"));
        this.propertyList.add(new PropertyHolder("desc",DB_TYPE_VARCHAR,"描述"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"交易时间"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除"));

    }

    @Override
    protected String getClassName() {
        return "HlbChangeRecord";
    }

    public static void main(String[] args) {
        HlbChangeRecordCreate loginCreate = new HlbChangeRecordCreate();
        loginCreate.createCode();
    }
}
