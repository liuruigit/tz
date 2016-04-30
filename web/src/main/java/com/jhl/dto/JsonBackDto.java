package com.jhl.dto;

import java.util.List;

/**
 * Created by vic wu on 2015/8/16.
 */
public class JsonBackDto<T> {
    private int code;

    private String msg;

    private List<T> list;

    public JsonBackDto() {
    }

    public JsonBackDto(int code, String msg, List<T> list) {
        this.code = code;
        this.msg = msg;
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
