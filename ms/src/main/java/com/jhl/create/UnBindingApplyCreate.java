package com.jhl.create;

/**
 * Created by Administrator on 2016/1/17.
 */
public class UnBindingApplyCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("ACC_ID",DB_TYPE_TINY_INT,"用户ID"));
        this.propertyList.add(new PropertyHolder("ATTACH",DB_TYPE_VARCHAR,"附件"));
        this.propertyList.add(new PropertyHolder("STATUS",DB_TYPE_DATETIME,"状态"));
        this.propertyList.add(new PropertyHolder("CREATE_TIME",DB_TYPE_VARCHAR,"创建日期"));
        this.propertyList.add(new PropertyHolder("UPDATE_TIME",DB_TYPE_TINY_INT,"更新日期"));
        this.propertyList.add(new PropertyHolder("DELETE_STATE",DB_TYPE_TINY_INT,"逻辑删除标志位"));
    }

    @Override
    protected String getClassName() {
        return "UnbindingApply";
    }

    public static void main(String[] args) {
        UnBindingApplyCreate loginCreate = new UnBindingApplyCreate();
        loginCreate.createCode();
    }
}
