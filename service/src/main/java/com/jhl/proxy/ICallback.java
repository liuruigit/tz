package com.jhl.proxy;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/2/17.
 */
public interface ICallback {

    /**
     * 资管回调
     * @param callbackPara
     * @throws Exception
     */
    void callback(JSONObject callbackPara) throws Exception;

    /**
     * 建行转账
     * @param res
     * @throws Exception
     */
    void jhTransaction(String res) throws Exception;

    /**
     * 参数解析
     * @param request
     * @return
     * @throws Exception
     */
    JSONObject parseReqParameter(HttpServletRequest request) throws Exception;

}
