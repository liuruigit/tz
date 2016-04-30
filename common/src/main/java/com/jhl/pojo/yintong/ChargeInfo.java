package com.jhl.pojo.yintong;

import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.Transient;

import java.util.Date;

/**
 * Created by vic wu on 2015/8/19.
 */
public class ChargeInfo {
    @Transient
    public static final int STATUS_CREATE=0;
    @Transient
    public static final int STATUS_WAIT=1;
    @Transient
    public static final int STATUS_SUCCESS=2;
    @PrimaryKey
    private Integer id;
    private String orderNo;
    @ForbidUpdate
    private Integer deleteState = 1;
    private Date createTime = new Date();
    private Integer userId;
    private String money;
    private String bankAccountNo;
    private String bankProvName;
    private String amountIn;
    private String outBizNo;
    private String notifyTime;
    private String asyResult;
    private String synResult;
    private int status;//0初始化1同步完成2异步完成



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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankProvName() {
        return bankProvName;
    }

    public void setBankProvName(String bankProvName) {
        this.bankProvName = bankProvName;
    }

    public String getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(String amountIn) {
        this.amountIn = amountIn;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
