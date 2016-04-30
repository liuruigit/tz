package com.jhl.pojos;

/**
 * Created by vic wu on 2015/8/14.
 */
public class VerifyCode {
    String tel;
    String code;
    String processing;//Y服务器处理中，""可执行
    long expriedTime;
    public static String PROCESSING_STATUS_N = "N";
    public static String PROCESSING_STATUS_Y = "Y";
    public VerifyCode() {}

    /**
     * 默认10分钟过期
     * @param code
     */
    public VerifyCode(String tel,String code) {
        long now = System.currentTimeMillis();
        this.tel = tel;
        this.code = code;
        this.processing = PROCESSING_STATUS_N;
        this.expriedTime = now + 10 * 60 * 1000;
    }

    public VerifyCode(String tel,String code,int min) {
        long now = System.currentTimeMillis();
        this.tel = tel;
        this.code = code;
        this.processing = PROCESSING_STATUS_N;
        this.expriedTime = now + min * 60 * 1000;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpriedTime() {
        return expriedTime;
    }

    public void setExpriedTime(long expriedTime) {
        this.expriedTime = expriedTime;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "tel='" + tel + '\'' +
                ", code='" + code + '\'' +
                ", expriedTime=" + expriedTime +
                '}';
    }
}
