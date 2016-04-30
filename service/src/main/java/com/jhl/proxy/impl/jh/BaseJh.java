package com.jhl.proxy.impl.jh;

import com.alibaba.fastjson.JSONObject;
import com.jhl.service.ChannelOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/26.
 */
@Service("jhCallback")
public abstract class BaseJh {
    @Autowired
    ChannelOrderService channelOrderService;
    private static final Logger logger = LoggerFactory.getLogger(BaseJh.class);

    /**
     * 解析xml数据
     * @param respMsg
     * @return
     * @throws Exception
     */
    public JSONObject parseResp(String respMsg) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("REQUEST_SN",JhClientMsg.getHeadNode(respMsg,"REQUEST_SN"));//请求序列码
        jsonObject.put("CUST_ID",JhClientMsg.getHeadNode(respMsg,"CUST_ID"));//客户号
        jsonObject.put("TX_CODE",JhClientMsg.getHeadNode(respMsg,"TX_CODE"));//请求交易码
        jsonObject.put("RETURN_CODE",JhClientMsg.getHeadNode(respMsg,"RETURN_CODE"));//返回码
        jsonObject.put("RETURN_MSG",JhClientMsg.getHeadNode(respMsg,"RETURN_MSG"));//返回码说明
        jsonObject = parseBizResp(jsonObject,respMsg);
        return jsonObject;
    }

    protected abstract JSONObject parseBizResp(JSONObject headJson,String xml) throws Exception;

}
