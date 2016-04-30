package com.jhl.dto;

/**
 * Created by Administrator on 2016/3/25.
 */
public class JhDto {

    String res;
    String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRes() {

        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "JhDto{" +
                "res='" + res + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
