package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * 资金变化记录
 * Created by amoszhou on 16/1/31.
 */
public class AccountChange implements Serializable {

    @PrimaryKey
    private Integer id;

    private Integer userId;

    private Long amount;

    private Long accMoney;

    private String type;

    private String orderNo;

    private String tranName;

    private Date createTime;

    private String extraMsg1;

    private String extraMsg2;

    private String extraMsg3;

    private String extraMsg4;

    public String getExtraMsg1() {
        return extraMsg1;
    }

    public void setExtraMsg1(String extraMsg1) {
        this.extraMsg1 = extraMsg1;
    }

    public String getExtraMsg2() {
        return extraMsg2;
    }

    public void setExtraMsg2(String extraMsg2) {
        this.extraMsg2 = extraMsg2;
    }

    public String getExtraMsg3() {
        return extraMsg3;
    }

    public void setExtraMsg3(String extraMsg3) {
        this.extraMsg3 = extraMsg3;
    }

    public String getExtraMsg4() {
        return extraMsg4;
    }

    public void setExtraMsg4(String extraMsg4) {
        this.extraMsg4 = extraMsg4;
    }

    public Long getAccMoney() {
        return accMoney;
    }

    public void setAccMoney(Long accMoney) {
        this.accMoney = accMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
