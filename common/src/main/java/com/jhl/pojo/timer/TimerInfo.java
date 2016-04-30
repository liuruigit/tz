package com.jhl.pojo.timer;

import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.ForbidUpdate;

import java.util.Date;

/**
 * Created by xin.fang on 2015/9/1.
 */
public class TimerInfo {

    @PrimaryKey
    private int ID;

    private Date time;

    private int type;

    private int eventId;

    @ForbidUpdate
    private int delete_state = 1;

    public TimerInfo() {
    }

    public TimerInfo(Date time, int type, int eventId) {
        this.time = time;
        this.type = type;
        this.eventId = eventId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getDelete_state() {
        return delete_state;
    }

    public void setDelete_state(int delete_state) {
        this.delete_state = delete_state;
    }
}
