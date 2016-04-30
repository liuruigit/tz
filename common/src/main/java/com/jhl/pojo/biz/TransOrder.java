package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by martin on 16/2/4.
 */
public class TransOrder implements Serializable{

    @PrimaryKey
    Integer id;
    /**创建日期*/
    Date createTime;
    /**投资表订单号*/
    Integer investOrderId ;
    /**项目原投资金额*/
    Long amount;
    /**'用户ID'*/
    Integer accId;
    /**'用户名称'*/
    String accName ;
    /**'标的ID'*/
    Integer proId;
    /**'折扣，单位%'*/
    Integer discount ;
    /**'状态:0发起认购1认购成功'*/
    Integer status ;
    /**'合同号'*/
    String contractNo;
    /**'转让金额:原认购金额折扣后的金额'*/
    Long transAmount ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(Integer investOrderId) {
        this.investOrderId = investOrderId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }
}
