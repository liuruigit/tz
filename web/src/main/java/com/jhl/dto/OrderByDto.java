package com.jhl.dto;

/**
 * Created by xin.fang on 2015/8/22.
 */
public class OrderByDto {

    private int key;

    private String name;

    public OrderByDto(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
