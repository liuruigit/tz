package com.jhl.dto;

import java.util.Date;

/**
 * Created by vic wu on 2015/9/5.
 */
public class RefundDto {

    double totalRefundMoney;//总还款金额
    double refundedMoney;//已还款金额
    double suggestRefundMoney;//建议还款金额
    String financingName;//项目名称
    String payWay;//还款方式
    Date date;
    Date expiredDate;

    public double getTotalRefundMoney() {
        return totalRefundMoney;
    }

    public void setTotalRefundMoney(double totalRefundMoney) {
        this.totalRefundMoney = totalRefundMoney;
    }

    public double getRefundedMoney() {
        return refundedMoney;
    }

    public void setRefundedMoney(double refundedMoney) {
        this.refundedMoney = refundedMoney;
    }

    public double getSuggestRefundMoney() {
        return suggestRefundMoney;
    }

    public void setSuggestRefundMoney(double suggestRefundMoney) {
        this.suggestRefundMoney = suggestRefundMoney;
    }

    public String getFinancingName() {
        return financingName;
    }

    public void setFinancingName(String financingName) {
        this.financingName = financingName;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
