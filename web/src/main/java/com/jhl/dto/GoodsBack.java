package com.jhl.dto;

import java.util.List;

/**
 * Created by xin.fang on 2015/8/16.
 */
public class GoodsBack {

    private int code;

    private String msg;

    private List<GoodsDto> list;

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

    public List<GoodsDto> getList() {
        return list;
    }

    public void setList(List<GoodsDto> list) {
        this.list = list;
    }
}
