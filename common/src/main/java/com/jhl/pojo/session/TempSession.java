package com.jhl.pojo.session;

/**
 * Created by vic wu on 2015/8/26.
 */
public class TempSession {

    String imei;
    String captcha;
    Integer loginFailedTimes;
    String isLock;
    long lastFailedTimes;
    long nextValildLoginTimes;

    public Integer getLoginFailedTimes() {
        return loginFailedTimes;
    }

    public void setLoginFailedTimes(Integer loginFailedTimes) {
        this.loginFailedTimes = loginFailedTimes;
    }

    public long getLastFailedTimes() {
        return lastFailedTimes;
    }

    public void setLastFailedTimes(long lastFailedTimes) {
        this.lastFailedTimes = lastFailedTimes;
    }

    public long getNextValildLoginTimes() {
        return nextValildLoginTimes;
    }

    public void setNextValildLoginTimes(long nextValildLoginTimes) {
        this.nextValildLoginTimes = nextValildLoginTimes;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }
}
