package com.jhl.create;

/**
 * Created by Administrator on 2016/1/14.
 */
public class ProjectInfoCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("name",DB_TYPE_VARCHAR,"项目名称","是","否"));
        this.propertyList.add(new PropertyHolder("propertyType",DB_TYPE_TINY_INT,"资产类型","是","1"));
        this.propertyList.add(new PropertyHolder("location",DB_TYPE_VARCHAR,"地域","是","否"));
        this.propertyList.add(new PropertyHolder("area",DB_TYPE_DOUBLE,"产权面积","是","否"));
        this.propertyList.add(new PropertyHolder("sellPrice",DB_TYPE_VARCHAR,"出售价格","是","否"));
        this.propertyList.add(new PropertyHolder("marketPrice",DB_TYPE_VARCHAR,"市场价","是","否"));
        this.propertyList.add(new PropertyHolder("propertyCert",DB_TYPE_TINY_INT,"产权证","是","否"));
        this.propertyList.add(new PropertyHolder("landCert",DB_TYPE_TINY_INT,"土地证","是","否"));
        this.propertyList.add(new PropertyHolder("propertyOwner",DB_TYPE_TINY_INT,"资产权属","是","否"));
        this.propertyList.add(new PropertyHolder("propertyRight",DB_TYPE_VARCHAR,"物权情况","是","否"));
        this.propertyList.add(new PropertyHolder("extraInfo",DB_TYPE_VARCHAR,"附加信息","是","否"));
        this.propertyList.add(new PropertyHolder("contract",DB_TYPE_VARCHAR,"买卖合同","是","否"));
        this.propertyList.add(new PropertyHolder("addedContract",DB_TYPE_VARCHAR,"补充协议","是","否"));
        this.propertyList.add(new PropertyHolder("propertyList",DB_TYPE_VARCHAR,"房源清单","是","否"));
        this.propertyList.add(new PropertyHolder("record",DB_TYPE_VARCHAR,"备案摘要","是","否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_VARCHAR,"创建时间","否","否"));
    }

    @Override
    protected String getClassName() {
        return "ProjectInfo";
    }

    public static void main(String[] args) {
        ProjectInfoCreate projectCreate = new ProjectInfoCreate();
        projectCreate.createCode();
    }
}
