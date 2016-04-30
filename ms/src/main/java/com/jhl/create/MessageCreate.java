package com.jhl.create;

/**
 * Created by martin on 16/1/24.
 */
public class MessageCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("title",DB_TYPE_VARCHAR,"标题","是" , "否"));
        this.propertyList.add(new PropertyHolder("content",DB_TYPE_VARCHAR,"富文本内容","是" , "否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DATETIME,"创建日期","是" , "否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_TINY_INT,"逻辑删除标志位：1正常0删除","是" , "否"));

    }

    @Override
    protected String getClassName() {
        return "Message";
    }

    public static void main(String[] args) {
        MessageCreate c = new MessageCreate();
        c.createCode();
    }
}
