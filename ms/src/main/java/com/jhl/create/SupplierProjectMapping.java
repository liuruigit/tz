package com.jhl.create;

/**
 * Created by Administrator on 2016/1/23.
 */
public class SupplierProjectMapping extends BaseCreate{

    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("gsCode",DB_TYPE_VARCHAR,"统一社会信用代码","是","否"));
        this.propertyList.add(new PropertyHolder("location",DB_TYPE_VARCHAR,"经营场所","是","否"));
        this.propertyList.add(new PropertyHolder("name",DB_TYPE_VARCHAR,"甲方名称","是","否"));
        this.propertyList.add(new PropertyHolder("totalAmount",DB_TYPE_VARCHAR,"合伙人认缴出资","是","否"));
        this.propertyList.add(new PropertyHolder("realTotalAmount",DB_TYPE_VARCHAR,"合伙人实缴","是","否"));
        this.propertyList.add(new PropertyHolder("supplierRealAmount",DB_TYPE_VARCHAR,"甲方已实缴","是","否"));
        this.propertyList.add(new PropertyHolder("supplierHoldPerc",DB_TYPE_VARCHAR,"甲方持有财产份额","是","否"));
        this.propertyList.add(new PropertyHolder("proId",DB_TYPE_VARCHAR,"配置value","是","否"));
    }

    @Override
    protected String getClassName() {
        return "SupplierProjectMapping";
    }

    public static void main(String[] args) {
        SupplierProjectMapping loginCreate = new SupplierProjectMapping();
        loginCreate.createCode();
    }

}
