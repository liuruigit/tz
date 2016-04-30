package com.jhl.create;

/**
 * Created by apple on 16/2/26.
 */
public class ChannelOrderCreate extends BaseCreate {
    @Override
    protected void setPropertyList() {
        this.propertyList.add(new PropertyHolder("amount",DB_TYPE_DOUBLE,"金额","是","否"));
        this.propertyList.add(new PropertyHolder("bankId",DB_TYPE_DOUBLE,"银行卡ID","是","否"));
        this.propertyList.add(new PropertyHolder("orderNo",DB_TYPE_DOUBLE,"请求订单号","是","否"));
        this.propertyList.add(new PropertyHolder("synResult",DB_TYPE_DOUBLE,"同步应答码","是","否"));
        this.propertyList.add(new PropertyHolder("asyResult",DB_TYPE_DOUBLE,"异步应答码","是","否"));
        this.propertyList.add(new PropertyHolder("notifyTime",DB_TYPE_DOUBLE,"通知时间","是","否"));
        this.propertyList.add(new PropertyHolder("tranFlow",DB_TYPE_DOUBLE,"外部订单号","是","否"));
        this.propertyList.add(new PropertyHolder("userId",DB_TYPE_DOUBLE,"用户ID","是","否"));
        this.propertyList.add(new PropertyHolder("status",DB_TYPE_DOUBLE,"0发起充值1平台接收处理中2到账","是","否"));
        this.propertyList.add(new PropertyHolder("createTime",DB_TYPE_DOUBLE,"创建时间","是","否"));
        this.propertyList.add(new PropertyHolder("deleteState",DB_TYPE_DOUBLE,"1有效","是","否"));
        this.propertyList.add(new PropertyHolder("type",DB_TYPE_DOUBLE,"订单类型0充值1提现","是","否"));


    }

    @Override
    protected String getClassName() {
        return "ChannelOrder";
    }

    public static void main(String[] args) {
        ChannelOrderCreate c = new ChannelOrderCreate();
        c.createCode();
    }
}
