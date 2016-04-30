package com.jhl.pojo;

import java.util.Date;

/**
 * Created by hallywu on 15/10/12.
 */
public class RefundOrderDto {
    Date date;
    String money;
    String name;
    int status;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
