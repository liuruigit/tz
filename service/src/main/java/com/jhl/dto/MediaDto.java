package com.jhl.dto;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class MediaDto {
    private String mediaName;
    private JSONObject jsonObject;

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
