package com.jhl.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by vic wu on 2015/8/17.
 */
public class UserDto {

    private String accName;
    private String pwd;
    private String adress;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAccName() {

        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

//    @Override
//    public void validate() {
//        notEmpty(getRealName(), "realName");
//        notEmpty(getEmail(), "email");
//        notEmpty(getCertNo(), "certNo");
//        notEmpty(getCertValidTime(), "certValidTime");
//    }
}
