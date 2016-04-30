package com.jhl.pojo.biz;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by martin on 16/2/21.
 */
public class MonthBilling implements Serializable {

    /**用户id */
    Integer accId;
    /**余额 */
    Long money;
    /**总资产 */
    Long property;
    /**收益 */
    Long accIncome;
    /**充值 */
    Long charge ;
    /**提现 */
    Long cash;
    /**创建日期 */
    Date createTime;
    /**更新日期 */
    Date updateTime;
    /**
     * 统计的月年
     */
    String yearMonth;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getProperty() {
        return property;
    }

    public void setProperty(Long property) {
        this.property = property;
    }

    public Long getAccIncome() {
        return accIncome;
    }

    public void setAccIncome(Long accIncome) {
        this.accIncome = accIncome;
    }

    public Long getCharge() {
        return charge;
    }

    public void setCharge(Long charge) {
        this.charge = charge;
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
