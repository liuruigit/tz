package com.jhl.pojo.weixin.function;

import java.util.Map;

/**
 * 模板消息请求数据实体类
 * Created by Administrator on 2016/3/20.
 */
public class Template {

    private String touser;//发送对象
    private String template_id;//模板ID
    private String url;//链接地址
    private Map<String, TemplateData> data;//详细数据
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Map<String, TemplateData> getData() {
        return data;
    }
    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }


}
