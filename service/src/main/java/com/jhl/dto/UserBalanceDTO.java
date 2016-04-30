package com.jhl.dto;

import com.jhl.pojo.biz.Account;
import com.jhl.util.MD5Util;

import java.io.Serializable;

/**
 * 更新帐户信息的DTO
 * Created by amoszhou on 16/1/31.
 */
public class UserBalanceDTO implements Serializable {

    private Integer userId;

    private Long balance;

    private Long frozenMoney;

    private Long investMoney;

    private Long accIncome;

    private String digest;

    private Integer version;

    public UserBalanceDTO() {
    }

    public UserBalanceDTO(Account account) {
        this.balance = account.getMoney();
        this.frozenMoney = account.getFrozenMoney();
        this.investMoney = account.getInvestMoney();
        this.version = account.getVersion();
        this.userId = account.getId();
        this.accIncome = account.getAccIncome();
    }


    /**
     * 生成当前帐户的摘要信息
     *
     * @return
     */
    public void generateDigest() {
        String origin = this.getUserId() + ":" + this.getBalance() + ":" +
                this.getInvestMoney() + ":" + this.getFrozenMoney()+ ":" + this.getAccIncome();
        try {
            String firstTime = MD5Util.getMD5String(origin);
            this.digest = MD5Util.getMD5String(firstTime);
        } catch (Exception e) {
        }
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getFrozenMoney() {
        return frozenMoney;
    }

    public void setFrozenMoney(Long frozenMoney) {
        this.frozenMoney = frozenMoney;
    }

    public Long getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(Long investMoney) {
        this.investMoney = investMoney;
    }

    public String getDigest() {
        return digest;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getAccIncome() {
        return accIncome;
    }

    public void setAccIncome(Long accIncome) {
        this.accIncome = accIncome;
    }

    @Override
    public String toString() {
        return "UserBalanceDTO{" +
                "userId=" + userId +
                ", balance=" + balance +
                ", frozenMoney=" + frozenMoney +
                ", investMoney=" + investMoney +
                ", accIncome=" + accIncome +
                ", digest='" + digest + '\'' +
                ", version=" + version +
                '}';
    }
}
