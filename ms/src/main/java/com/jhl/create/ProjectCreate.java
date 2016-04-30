package com.jhl.create;

/**
 * Created by Administrator on 2016/1/14.
 */
public class ProjectCreate extends BaseCreate {

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("name",DB_TYPE_VARCHAR,"项目名称","是","否"));
        this.propertyList.add(new PropertyHolder("no",DB_TYPE_VARCHAR,"产品编号","是","否"));
        this.propertyList.add(new PropertyHolder("guarantee",DB_TYPE_TINY_INT,"保障级别","是","否"));
        this.propertyList.add(new PropertyHolder("payInterestWay",DB_TYPE_TINY_INT,"付息方式","是","1"));
        this.propertyList.add(new PropertyHolder("amount",DB_TYPE_DOUBLE,"融资金额","是","否"));
        this.propertyList.add(new PropertyHolder("selledAmount",DB_TYPE_DOUBLE,"已融资金额","是","否"));
        this.propertyList.add(new PropertyHolder("marketPrice",DB_TYPE_DOUBLE,"市场价","是","否"));
        this.propertyList.add(new PropertyHolder("min",DB_TYPE_DOUBLE,"起投金额","是","否"));
        this.propertyList.add(new PropertyHolder("step",DB_TYPE_DOUBLE,"累加金额","是","否"));
        this.propertyList.add(new PropertyHolder("limit",DB_TYPE_DOUBLE,"限投金额","是","否"));
        this.propertyList.add(new PropertyHolder("annualRate",DB_TYPE_DOUBLE,"预期年化收益率","是","否"));
        this.propertyList.add(new PropertyHolder("serviceRate",DB_TYPE_DOUBLE,"服务费率","是","否"));
        this.propertyList.add(new PropertyHolder("openDate",DB_TYPE_DATETIME,"开放购买时间","是","否"));
        this.propertyList.add(new PropertyHolder("status",DB_TYPE_TINY_INT,"状态","是","否"));
        this.propertyList.add(new PropertyHolder("desr",DB_TYPE_VARCHAR,"项目描述","是","否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_VARCHAR,"创建时间","否","否"));
        this.propertyList.add(new PropertyHolder("recommend",DB_TYPE_TINY_INT,"是否推荐","是","否"));
        this.propertyList.add(new PropertyHolder("extra1",DB_TYPE_VARCHAR,"预留信息1","是","否"));
        this.propertyList.add(new PropertyHolder("extra2",DB_TYPE_VARCHAR,"预留信息2","是","否"));
        this.propertyList.add(new PropertyHolder("extra3",DB_TYPE_VARCHAR,"预留信息3","是","否"));
    }

    @Override
    protected String getClassName() {
        return "Project";
    }

    public static void main(String[] args) {
        ProjectCreate projectCreate = new ProjectCreate();
        projectCreate.createCode();
    }
}
