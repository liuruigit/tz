package com.jhl.dto;

/**
 * Created by Administrator on 2016/3/19.
 */
public class ChannelOrderDto extends BaseDto {

    String type;
    String date;
    String range;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "ChannelOrderDto{" +
                "type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", range='" + range + '\'' +
                "} " + super.toString();
    }


}
