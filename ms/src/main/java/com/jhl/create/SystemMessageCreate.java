package com.jhl.create;

/**
 * Created by martin on 16/1/24.
 */
public class SystemMessageCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("body",DB_TYPE_VARCHAR,"消息内容","是" , "否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建日期","否" , "否"));
        this.propertyList.add(new PropertyHolder("updateTime",DB_TYPE_DATETIME,"更新日期","否" , "否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位：1正常0删除","否" , "否"));
    }

    @Override
    protected String getClassName() {
        return "SystemMessage";
    }

    public static void main(String[] args) {
        SystemMessageCreate c = new SystemMessageCreate();
        c.createCode();
    }
}
