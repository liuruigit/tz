package com.jhl.proxy.impl.jh;

import com.alibaba.fastjson.JSONObject;
import com.jhl.util.HttpClient431Util;
import com.jhl.util.SeqNoUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/26.
 * 待处理转账交易结果查询实现类
 */
public class JhWaitAutomaticDetails extends BaseJh{



    /**
     * （6W0600）交易结果查询
     * <p> Create Date: 2014-5-10 </p>
     * @return
     */
    private static String buildReqXml(String requestSn, String custId, String userId, String password, String requestSn1){
        StringBuffer reqXml = new StringBuffer();
        reqXml.append("<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?>");
        reqXml.append("<TX>")
                .append("<REQUEST_SN>").append(requestSn).append("</REQUEST_SN>").//请求序列码
                append("<CUST_ID>").append(custId).append("</CUST_ID>").//客户号
                append("<USER_ID>").append(userId).append("</USER_ID>").//操作员号
                append("<PASSWORD>").append(password).append("</PASSWORD>").//密码
                append("<TX_CODE>6W0600</TX_CODE>").//请求交易码
                append("<LANGUAGE>CN</LANGUAGE>").
                append("<TX_INFO>").
                append("<REQUEST_SN1>").append(requestSn1).append("</REQUEST_SN1>").//原交易请求序列码
                append("</TX_INFO>").
                append("</TX>");
        return reqXml.toString();

    }

    @Override
    protected JSONObject parseBizResp(JSONObject headJson, String xml) throws Exception {
        JSONObject respJson = headJson;
        respJson.put("CREDIT_NO",JhClientMsg.getTxtInfo(xml,"CREDIT_NO"));//凭证号
        respJson.put("DEAL_RESULT",JhClientMsg.getTxtInfo(xml,"DEAL_RESULT"));//交易处理结果
        respJson.put("MESSAGE",JhClientMsg.getTxtInfo(xml,"MESSAGE"));//错误原因
        respJson.put("REM1",JhClientMsg.getTxtInfo(xml,"REM1"));//备注1
        respJson.put("REM2",JhClientMsg.getTxtInfo(xml,"REM2"));//备注2
        return respJson;
    }

    public static void main(String[] args) throws Exception{
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("requestXml", JhWaitAutomaticDetails.buildReqXml(SeqNoUtil.nextSeqNo(),"SZP717856009#002","WLPT05","999999","201510290102"));
        String res = HttpClient431Util.sendXmlData("http://58.0.99.176:8888",paramMap);
        System.out.println(res);
        JhWaitAutomaticDetails jhBinding = new JhWaitAutomaticDetails();
        System.out.println(jhBinding.parseResp(res).toJSONString());
    }

}
