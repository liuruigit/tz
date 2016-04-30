package com.sz.jhl.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2016/3/30.
 */
@ConfigurationProperties(prefix = "jh",locations = "classpath:ccb_config.properties")
public class Config {

    private String cusId;
    private String userId;
    private String passward;
    private String account;
    private String connect;
    private String api;
    private String key;
    private String commet;

    public String getCommet() {
        return commet;
    }

    public void setCommet(String commet) {
        this.commet = commet;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
