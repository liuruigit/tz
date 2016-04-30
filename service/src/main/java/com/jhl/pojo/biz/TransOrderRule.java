package com.jhl.pojo.biz;


/**
 * Created by martin on 16/2/4.
 * 转让投资规则
 */
public class TransOrderRule {

    //#平台收取费率方式，0固定，1百分比
    private int serviceFeeType;

    //#平台费率
    private int serviceFee;

    //#最小持有期限天
    private int limitDay;

    //#最小收益率（百分比）
    private int minRate;

    //#实际收益大于最小收益,是否补差
    private int paybackIfNotEnough;

    //#折扣定义,逗号隔开,单位百分比,如95,为95%折
    private String[] discounts;

    public int getServiceFeeType() {
        return serviceFeeType;
    }

    public void setServiceFeeType(int serviceFeeType) {
        this.serviceFeeType = serviceFeeType;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(int limitDay) {
        this.limitDay = limitDay;
    }

    public int getMinRate() {
        return minRate;
    }

    public void setMinRate(int minRate) {
        this.minRate = minRate;
    }

    public int getPaybackIfNotEnough() {
        return paybackIfNotEnough;
    }

    public void setPaybackIfNotEnough(int paybackIfNotEnough) {
        this.paybackIfNotEnough = paybackIfNotEnough;
    }

    public String[] getDiscounts() {
        return discounts;
    }

    public void setDiscounts(String[] discounts) {
        this.discounts = discounts;
    }
}
