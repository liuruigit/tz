package com.jhl.pojo;

import java.util.List;

/**
 * Created by vic wu on 2015/10/15.
 */
public class BenifitDayInfo {

    String when;
    String money;
    List<BenifitDayDetailInfo> detail;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public List<BenifitDayDetailInfo> getDetail() {
        return detail;
    }

    public void setDetail(List<BenifitDayDetailInfo> detail) {
        this.detail = detail;
    }



}
