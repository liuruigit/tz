package com.sz.jhl.appliaction;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.sz.jhl.Config.Config;
import com.sz.jhl.jh.JhAccontDetail;
import com.sz.jhl.util.DateUtil;
import com.sz.jhl.util.HttpClient431Util;
import com.sz.jhl.util.MD5Util;
import com.sz.jhl.util.SeqNoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/3/12.
 * 建行转账交易查询
 */
@Component
public class JhTransQueryTask {

    @Autowired
    Config config;
    JhAccontDetail jhAccontDetail = new JhAccontDetail();
    private Logger logger = Logger.getLogger(JhTransQueryTask.class);
    /** API Server已完成处理的页数 */
    public static int pageSize = 50;
    private AtomicInteger curr_page = new AtomicInteger(1);
    private String postStr = "";

    @Scheduled(cron = "0 0/10 * * * ?")
    public void excuteQuery(){
        JhQueryParam jhQueryParam = new JhQueryParam(config.getCusId(),
                config.getUserId(),config.getPassward(),config.getAccount());
        logger.info("build para success:" + jhQueryParam.toString());
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("requestXml", jhAccontDetail.buildReqXml(SeqNoUtil.nextSeqNo(),
                jhQueryParam.custId,
                jhQueryParam.userId,
                jhQueryParam.password,
                jhQueryParam.account,
                jhQueryParam.beginDate,
                jhQueryParam.endDate,
                jhQueryParam.bargainFlag,
                jhQueryParam.checkAccNo,
                jhQueryParam.checkAccName,
                jhQueryParam.remark,
                jhQueryParam.lowAmt,
                jhQueryParam.highAmt,
                Integer.toString(curr_page.get()),
                postStr,
                jhQueryParam.totalRecord,
                jhQueryParam.detNo));
        try {
            logger.info("begin send data:"+paramMap.get("requestXml"));
            String res = HttpClient431Util.sendXmlData(config.getConnect(),paramMap);
            if (Strings.isNullOrEmpty(res)){
                logger.warn("CCB Response empty String！");
                return;
            }
            JSONObject result = jhAccontDetail.parseResp(res);
            nextPage(result);
            String apiRes = HttpClient431Util.doPost(buildParams(res),config.getApi());
            logger.info("excute jhl api successed:"+apiRes);

        } catch (Exception e) {
            logger.error("定时查询建行异常",e);
        }
    }

    /**
     *  <POSTSTR>+5+1+2+</POSTSTR> ==> <POSTSTR>+【总记录数】+【起记录】+【止记录】+</POSTSTR>
     * 当且仅当POSTSTR返回的止记录 - 起记录 = pageSize - 1，并且总页数 > 当前页数时翻页查询
     * @param jsonObject
     */
    private void nextPage(JSONObject jsonObject) {
        if (jsonObject == null)return;
        logger.info("CCB response data is "+jsonObject.toJSONString());
        try{
            int totalPage = jsonObject.getInteger("TOTAL_PAGE");
            postStr = jsonObject.getString("POSTSTR");
            if(!Strings.isNullOrEmpty(postStr)){
                String[] val = postStr.split("\\+");
                if (val.length != 4)return;
                int from = Integer.parseInt(val[2]);
                int to = Integer.parseInt(val[3]);
                if ((to - from) == (pageSize - 1) && curr_page.get() < totalPage){
                    curr_page.incrementAndGet();
                }
            }
            logger.info("TOTAL_PAGE is "+totalPage+",cur_page is" + curr_page.get()+",postStr:"+postStr);
        }catch (Exception e){
            logger.error("参数解析异常",e);
        }
    }

    private Map<String, String> buildParams(String res) throws Exception{
        String base = res + config.getKey();
        System.out.println("原始加密字符串:"+base);
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", MD5Util.getMD5String(base));
        params.put("res",res);
        return params;
    }
}

class JhQueryParam{
    String custId = "";//客户号
    String userId = "";//操作员号
    String password = "";//密码
    String account = "";//账号
    String beginDate = "";//开始日期
    String endDate = "";//结束日期
    String bargainFlag = "";//交易方向
    String checkAccNo = "";//对方账户
    String checkAccName = "";//对方账户名称
    String remark = "";//摘要
    String lowAmt = "";//最小金额
    String highAmt = "";//最大金额
    String page = "1";//起始页
    String postStr;//定位串
    String totalRecord = JhTransQueryTask.pageSize+"";//每页记录数
    String detNo = "";//活存账户明细号

    JhQueryParam (  String custId,
                    String userId,
                    String password,
                    String account ){
        beginDate = DateUtil.format(DateUtil.yesterday(),"yyyyMMdd");//昨日
        endDate = DateUtil.format(new Date(),"yyyyMMdd");//今日
        this.custId = custId;
        this.userId = userId;
        this.password = password;
        this.account = account;
    }

    @Override
    public String toString() {
        return "JhQueryParam{" +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", bargainFlag='" + bargainFlag + '\'' +
                ", checkAccNo='" + checkAccNo + '\'' +
                ", checkAccName='" + checkAccName + '\'' +
                ", remark='" + remark + '\'' +
                ", lowAmt='" + lowAmt + '\'' +
                ", highAmt='" + highAmt + '\'' +
                ", page='" + page + '\'' +
                ", postStr='" + postStr + '\'' +
                ", totalRecord='" + totalRecord + '\'' +
                ", detNo='" + detNo + '\'' +
                '}';
    }

    public static void main(String[] args) {
//        JhQueryParam jhQueryParam = new JhQueryParam("","","","");
//        System.out.println(jhQueryParam.toString());
//        System.out.println(JhAccontDetail.buildReqXml(SeqNoUtil.nextSeqNo(),
//                jhQueryParam.custId,
//                jhQueryParam.userId,
//                jhQueryParam.password,
//                jhQueryParam.account,
//                jhQueryParam.beginDate,
//                jhQueryParam.endDate,
//                jhQueryParam.bargainFlag,
//                jhQueryParam.checkAccNo,
//                jhQueryParam.checkAccName,
//                jhQueryParam.remark,
//                jhQueryParam.lowAmt,
//                jhQueryParam.highAmt,
//                jhQueryParam.page,
//                jhQueryParam.postStr,
//                jhQueryParam.totalRecord,
//                jhQueryParam.detNo));
        AtomicInteger aa = new AtomicInteger(0);
        System.out.println(aa.incrementAndGet());
    }
}
