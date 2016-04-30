package com.jhl.pojo.biz;

import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.PrimaryKey;
import com.jhl.util.DESUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * Created by hallywu on 15/9/25.
 * todo 加密解密
 * 银行卡
 */
public class BankCard {
    @PrimaryKey
    private Integer id;
    /** 银行名称*/
    String bankName;
    /** 名称*/
    String name;
    /** 用户ID*/
    int userId;
    /** 银行卡号*/
    String bankCardNo;
    /** 省*/
    String pro;
    /** 市*/
    String city;
    /** 支行*/
    String branch;
    /** code*/
    String bankCode;
    /** 是否默认卡*/
    int isDefault;
    /** 协议号*/
    String prcptcd;
    /** 签约协议号*/
    String noAgree;
    /** 绑定的手机号*/
    String mobile;
    @ForbidUpdate
    private Integer deleteState = 1;
    private Date createTime = new Date();

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum DEFAULT {
        /**
         * 默认卡
         */
        YES(0),
        /**
         * 非默认卡
         */
        NO(1);

        int value;

        DEFAULT(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * 数据解密
     */
    public void parseBank() {
        this.mobile = DESUtil.getDesString(mobile);
        this.name = DESUtil.getDesString(name);
        this.bankCardNo = DESUtil.getDesString(bankCardNo);
    }

    /**
     * 数据加密
     * @return
     */
    public void encrptBank() {
        this.mobile = DESUtil.getEncString(mobile);
        this.name = DESUtil.getEncString(name);
        this.bankCardNo = DESUtil.getEncString(bankCardNo);
    }


    public String getPrcptcd() {
        return prcptcd;
    }

    public void setPrcptcd(String prcptcd) {
        this.prcptcd = prcptcd;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNoAgree() {
        return noAgree;
    }

    public void setNoAgree(String noAgree) {
        this.noAgree = noAgree;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
