package com.jhl.dto;


import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/3/19.
 */
public class MenuDto {

    private Long date;
    private JSONObject jsonMenu;


    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public JSONObject getJsonMenu() {
        return jsonMenu;
    }

    public void setJsonMenu(JSONObject jsonMenu) {
        this.jsonMenu = jsonMenu;
    }
}
