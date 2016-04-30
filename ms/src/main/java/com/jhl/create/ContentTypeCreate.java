package com.jhl.create;

/**
 * 网站内容类型
 */
public class ContentTypeCreate extends BaseCreate{

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("type_key",DB_TYPE_VARCHAR,"网站内容KEY","是","否"));
        this.propertyList.add(new PropertyHolder("type_des",DB_TYPE_VARCHAR,"网站内容类型描述","是","否"));
    }

    @Override
    protected String getClassName() {
        return "ContentType";
    }

    public static void main(String[] args) {
        ContentTypeCreate loginCreate = new ContentTypeCreate();
        loginCreate.createCode();
    }

}
