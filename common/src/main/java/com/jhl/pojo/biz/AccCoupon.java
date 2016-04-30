package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.util.Date;

/**
 * Created by hallywu on 16/2/16.
 */
public class AccCoupon {

    @PrimaryKey
    private Integer id;
    /**
     * 用户ID
     */
    private int accId;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 是否使用
     */
    private Integer status;
    /**
     * 投资券ID
     */
    private Integer couponId;
    /**
     * 逻辑删除标志位
     */
    private Integer deleteState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 有效日期
     */
    private Date validDate;
    /**
     * 使用时间
     */
    private Date usedTime;

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    @Override
    public String toString() {
        return "AccCoupon{" +
                "accId=" + accId +
                ", id=" + id +
                ", amount=" + amount +
                ", status=" + status +
                ", couponId=" + couponId +
                ", deleteState=" + deleteState +
                ", createTime=" + createTime +
                ", validDate=" + validDate +
                ", usedTime=" + usedTime +
                '}';
    }
}
