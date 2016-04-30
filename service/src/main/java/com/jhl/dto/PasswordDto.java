package com.jhl.dto;

/**
 * Created by Administrator on 2016/2/18.
 */
public class PasswordDto extends BaseDto{
    String pwd;
    String tradePwd;
    String mobile;

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
