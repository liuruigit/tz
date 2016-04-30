package com.jhl.proxy.impl.jh;

import com.alibaba.fastjson.JSONObject;
import com.jhl.util.HttpClient431Util;
import com.jhl.util.SeqNoUtil;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/26.
 * 一点接入活期账户明细查询
 */
public class JhAccontDetail extends BaseJh{

    /**
     * （6WY101）一点接入活期账户明细查询
     * <p> Create Date: 2014-5-10 </p>
     * @return
     */
    public static String buildReqXml(String requestSn, String custId, String userId, String password,
                                      String accno1, String startdate, String enddate, String bargainFlag, String checkAccNo,
                                      String checkAccName, String remark, String lowAmt, String highAmt, String page,
                                      String poststr, String totalRecord, String detNo){
        StringBuffer reqXml = new StringBuffer();
        reqXml.append("<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\" ?>");
        reqXml.append("<TX>")
                .append("<REQUEST_SN>").append(requestSn).append("</REQUEST_SN>").//请求序列码
                append("<CUST_ID>").append(custId).append("</CUST_ID>").//客户号
                append("<USER_ID>").append(userId).append("</USER_ID>").//操作员号
                append("<PASSWORD>").append(password).append("</PASSWORD>").//密码
                append("<TX_CODE>6WY101</TX_CODE>").
                append("<LANGUAGE>CN</LANGUAGE>").
                append("<TX_INFO>").
                append("<ACCNO1>").append(accno1).append("</ACCNO1>").
                append("<STARTDATE>").append(startdate).append("</STARTDATE>").
                append("<ENDDATE>").append(enddate).append("</ENDDATE>").
                append("<BARGAIN_FLAG>").append(bargainFlag).append("</BARGAIN_FLAG>").
                append("<CHECK_ACC_NO>").append(checkAccNo).append("</CHECK_ACC_NO>").
                append("<CHECK_ACC_NAME>").append(checkAccName).append("</CHECK_ACC_NAME>").
                append("<REMARK>").append(remark).append("</REMARK>").
                append("<LOW_AMT>").append(lowAmt).append("</LOW_AMT>").
                append("<HIGH_AMT>").append(highAmt).append("</HIGH_AMT>").
                append("<PAGE>").append(page).append("</PAGE>").
                append("<POSTSTR>").append(poststr).append("</POSTSTR>").
                append("<TOTAL_RECORD>").append(totalRecord).append("</TOTAL_RECORD>").
                append("<DET_NO>").append(detNo).append("</DET_NO>").
                append("</TX_INFO>").
                append("</TX>");
        return reqXml.toString();

    }

    @Override
    protected JSONObject parseBizResp(JSONObject headJson, String xml) throws Exception {
        JSONObject respJson = headJson;
        respJson.put("ACCNO1",JhClientMsg.getTxtInfo(xml,"ACCNO1"));//本方账号
        respJson.put("CURR_COD",JhClientMsg.getTxtInfo(xml,"CURR_COD"));//币种
        respJson.put("ACC_NAME",JhClientMsg.getTxtInfo(xml,"ACC_NAME"));//本方账号名称
        respJson.put("ACC_ORGAN",JhClientMsg.getTxtInfo(xml,"ACC_ORGAN"));//本方账号开户机构
        respJson.put("ACC_STATE",JhClientMsg.getTxtInfo(xml,"ACC_STATE"));//本方账号状态
        respJson.put("INTR",JhClientMsg.getTxtInfo(xml,"INTR"));//利率
        respJson.put("TOTAL_PAGE",JhClientMsg.getTxtInfo(xml,"TOTAL_PAGE"));//总页次
        respJson.put("PAGE",JhClientMsg.getTxtInfo(xml,"PAGE"));//当前页次
        respJson.put("POSTSTR",JhClientMsg.getTxtInfo(xml,"POSTSTR"));//定位串
        respJson.put("FLAG",JhClientMsg.getTxtInfo(xml,"FLAG"));//是否可生成标识位
        respJson.put("FILE_LOCSTR",JhClientMsg.getTxtInfo(xml,"FILE_LOCSTR"));//生成文件定位串
        List<Element> detailList = JhClientMsg.getDetailListInfo(xml, "DETAILLIST");
        List<JSONObject> detailJsonList = new ArrayList<>();
        for(Element detailInfo : detailList) {
//            if ("金葫芦充值转账".equalsIgnoreCase(detailInfo.element("DET").getData().toString())){
                JSONObject detailInfoJson = new JSONObject();
                detailInfoJson.put("TRANDATE", detailInfo.element("TRANDATE").getData().toString());//交易日期
                detailInfoJson.put("TRANTIME", detailInfo.element("TRANTIME").getData().toString());//交易时间
                detailInfoJson.put("CRE_TYP", detailInfo.element("CRE_TYP").getData().toString());//凭证种类
                detailInfoJson.put("CRE_NO", detailInfo.element("CRE_NO").getData().toString());//凭证号码
                detailInfoJson.put("MESSAGE", detailInfo.element("MESSAGE").getData().toString());//摘要
                detailInfoJson.put("AMT", detailInfo.element("AMT").getData().toString());//发生额
                detailInfoJson.put("AMT1", detailInfo.element("AMT1").getData().toString());//余额
                detailInfoJson.put("FLAG1", detailInfo.element("FLAG1").getData().toString());//借贷标志
                detailInfoJson.put("ACCNO2", detailInfo.element("ACCNO2").getData().toString());//对方账号
                detailInfoJson.put("ACC_NAME1", detailInfo.element("ACC_NAME1").getData().toString());//对方户名
                detailInfoJson.put("FLAG2", detailInfo.element("FLAG2").getData().toString());//交易钞汇标志
                detailInfoJson.put("TRAN_FLOW", detailInfo.element("TRAN_FLOW").getData().toString());//交易流水号
                detailInfoJson.put("BFLOW", detailInfo.element("BFLOW").getData().toString());//企业支付流水号
                detailInfoJson.put("DET_NO", detailInfo.element("DET_NO").getData().toString());//活存帐户明细号
                detailInfoJson.put("DET", detailInfo.element("DET").getData().toString());//备注
                detailJsonList.add(detailInfoJson);
            }
//        }
        respJson.put("DETAILLIST",detailJsonList);//生成文件定位串
        return respJson;
    }

    public static void main(String[] args) throws Exception{
        JhAccontDetail jhCurrent = new JhAccontDetail();
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("requestXml", JhAccontDetail.buildReqXml(SeqNoUtil.nextSeqNo(),"SZ44200009175197501","WLPT01",
                "52672325","44250110136800000299", "20160330","20160403","","","","","","","1","","200",""));
        String res = HttpClient431Util.sendXmlData("http://192.168.1.150:8888",paramMap);


//        System.out.println(res);
        System.out.println(jhCurrent.parseResp(res).toJSONString());


//        paramMap.put("requestXml", JhAccontDetail.buildReqXml(SeqNoUtil.nextSeqNo(),"SZ44200009175197501","WLPT01",
//                "52672325","44250110136800000299", "20160331","20160403","","","","","","","1","","200",""));
    }
}

