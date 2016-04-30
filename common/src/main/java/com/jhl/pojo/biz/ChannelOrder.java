package com.jhl.pojo.biz;

import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.Transient;

import java.util.Date;

/**
 * Created by vic wu on 2015/8/19.
 */
public class ChannelOrder {
    @Transient
    public static final int STATUS_CREATE=0;
    @Transient
    public static final int STATUS_SUCCESS=2;//金运通申请提现成功|充值到账成功
    @Transient
    public static final int STATUS_FAILED=4;//失败
    @Transient
    public static final int STATUS_TRANSACTION_SUCCESS =3;//到账成功
    @Transient
    public static final int STATUS_AGREE=1;//管理员同意提现,金运通未返回申请结果

    @Transient
    public static final int CHANNEL_JYT=0;//金运通到账成功
    @Transient
    public static final int CHANNEL_JH=1;//管理员同意提现,金运通未返回申请结果

    @PrimaryKey
    private Integer id;
    private String orderNo;
    @ForbidUpdate
    private Integer deleteState = 1;
    private Date createTime = new Date();
    private Integer bankId;
    private Integer userId;
    private Long amount;
    private String tranFlow;
    private Date notifyTime;
    private String asyResult;
    private String synResult;
    private int status;
    private int type;
    private int channel;//对账渠道字段

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTranFlow() {
        return tranFlow;
    }

    public void setTranFlow(String tranFlow) {
        this.tranFlow = tranFlow;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getAsyResult() {
        return asyResult;
    }

    public void setAsyResult(String asyResult) {
        this.asyResult = asyResult;
    }

    public String getSynResult() {
        return synResult;
    }

    public void setSynResult(String synResult) {
        this.synResult = synResult;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public enum Type{
        VALIDATE(0),BINDING(1),CHARGE(2),CASH(3);

        Type(int val){
            this.value = val;
        }

        private int value;

        public int getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "ChannelOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", deleteState=" + deleteState +
                ", createTime=" + createTime +
                ", bankId=" + bankId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", tranFlow='" + tranFlow + '\'' +
                ", notifyTime=" + notifyTime +
                ", asyResult='" + asyResult + '\'' +
                ", synResult='" + synResult + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
