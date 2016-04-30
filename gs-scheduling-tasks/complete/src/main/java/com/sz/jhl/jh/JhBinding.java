package com.sz.jhl.jh;

import com.alibaba.fastjson.JSONObject;
import com.sz.jhl.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2016/2/26.
 */
public class JhBinding extends BaseJh{

    private static final Logger logger = LoggerFactory.getLogger(JhBinding.class);
    public static String TRAN_CODE = "TR4003";//交易请请求码

    /*public void sendReq(){
        String response = sendMsg(buildReqXml());
    }*/

    /**
     * （6W0100）余额查询交易
     * <p> Create Date: 2014-5-10 </p>
     * @return
     */
    private static String buildReqXml(String requestSn, String custId, String userId, String password, String accNo){
            StringBuffer reqXml = new StringBuffer();
            reqXml.append("<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?>");
            reqXml.append("<TX>")
                    .append("<REQUEST_SN>").append(requestSn).append("</REQUEST_SN>").//请求序列码
                     append("<CUST_ID>").append(custId).append("</CUST_ID>").//客户号
                     append("<USER_ID>").append(userId).append("</USER_ID>").//操作员号
                     append("<PASSWORD>").append(password).append("</PASSWORD>").//密码
                     append("<TX_CODE>6W0100</TX_CODE>").
                     append("<LANGUAGE>CN</LANGUAGE>").
                     append("<TX_INFO>").
                     append("<ACC_NO>").append(accNo).append("</ACC_NO>").
                     append("</TX_INFO>").
                    append("</TX>");
            return  reqXml.toString();

    }

    @Override
    protected JSONObject parseBizResp(JSONObject headJson, String xml) throws Exception {
        JSONObject respJson = headJson;
        respJson.put("ACC_NO", XmlUtil.getTxtInfo(xml,"ACC_NO"));//帐户号
        respJson.put("BALANCE", XmlUtil.getTxtInfo(xml,"BALANCE"));//余额
        respJson.put("BALANCE1", XmlUtil.getTxtInfo(xml,"BALANCE1"));//可用余额
        respJson.put("INTEREST", XmlUtil.getTxtInfo(xml,"INTEREST"));//帐户利息
        respJson.put("INTEREST_RATE", XmlUtil.getTxtInfo(xml,"INTEREST_RATE"));//帐户利率
        respJson.put("ACC_STATUS", XmlUtil.getTxtInfo(xml,"ACC_STATUS"));//账户状态
        respJson.put("RESV_NAME1", XmlUtil.getTxtInfo(xml,"RESV_NAME1"));//分行自定义输出名称1
        respJson.put("RESV1", XmlUtil.getTxtInfo(xml,"RESV1"));//分行自定义输出内容1
        respJson.put("RESV_NAME2", XmlUtil.getTxtInfo(xml,"RESV_NAME2"));//分行自定义输出名称2
        respJson.put("RESV2", XmlUtil.getTxtInfo(xml,"RESV2"));//分行自定义输出内容2
        respJson.put("REM1", XmlUtil.getTxtInfo(xml,"REM1"));//备注1
        respJson.put("REM2", XmlUtil.getTxtInfo(xml,"REM2"));//备注2
        return respJson;
    }

    public static void main(String[] args) throws Exception{
//        Map<String,String> paramMap = new HashMap<String,String>();
//        paramMap.put("requestXml", JhBinding.buildReqXml(SeqNoUtil.nextSeqNo(),"SZP717856009#002","WLPT05","999999","44201501100052511215"));
//        String res = HttpClient431Util.sendXmlData("http://58.0.99.176:8888",paramMap);
//        System.out.println(res);
//        JhBinding jhBinding = new JhBinding();
//        System.out.println(jhBinding.parseResp(res).toJSONString());
    }


}
