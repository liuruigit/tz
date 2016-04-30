package com.jhl.dto;

/**
 * Created by Administrator on 2016/3/13.
 */
public class NoticeDto extends BaseDto {

    int count;
    String type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
