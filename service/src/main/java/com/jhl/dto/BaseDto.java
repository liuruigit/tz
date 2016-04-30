package com.jhl.dto;

/**
 * Created by Administrator on 2016/2/18.
 * API - Server接收參數對象
 */
public class BaseDto {

    /**
     * 客户端类型 APP WEB WX
     */
    String client;
    /**
     * 请求版本号
     */
    String version;
    /**
     * 设备号
     */
    String imei;
    /**
     * token
     */
    String token;

    /**
     * 图片验证码缓存KEY
     */
    String captchaKey;
    /**
     * 图片验证码
     */
    String captchaVal;
    /**
     * 分页页码
     */
    String page;
    /**
     * 短信验证码
     */
    String code;
    /**
     * 支付密码
     */
    String tradePwd;
    /**
     * 排序
     */
    String order;
    /**
     * 排序名称
     */
    String orderName;
    /**
     * 短信NO
     */
    String smsNo;

    /**
     * 支付TOKEN
     */
    String payToken;

    public String getPayToken() {
        return payToken;
    }

    public void setPayToken(String payToken) {
        this.payToken = payToken;
    }

    public String getSmsNo() {
        return smsNo;
    }

    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCaptchaVal() {
        return captchaVal;
    }

    public void setCaptchaVal(String captchaVal) {
        this.captchaVal = captchaVal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "client='" + client + '\'' +
                ", version='" + version + '\'' +
                ", imei='" + imei + '\'' +
                ", token='" + token + '\'' +
                ", captchaKey='" + captchaKey + '\'' +
                ", captchaVal='" + captchaVal + '\'' +
                ", page='" + page + '\'' +
                ", code='" + code + '\'' +
                ", tradePwd='" + tradePwd + '\'' +
                ", order='" + order + '\'' +
                ", orderName='" + orderName + '\'' +
                ", smsNo='" + smsNo + '\'' +
                '}';
    }
}
