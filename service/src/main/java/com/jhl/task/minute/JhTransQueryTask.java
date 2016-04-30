package com.jhl.task.minute;

import com.google.common.base.Strings;
import com.jhl.constant.ConfigKey;
import com.jhl.proxy.impl.jh.JhAccontDetail;
import com.jhl.service.SysConfig;
import com.jhl.util.DateUtil;
import com.jhl.util.HttpClient431Util;
import com.jhl.util.MD5Util;
import com.jhl.util.SeqNoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/12.
 * 建行转账交易查询
 */
//@Component
public class JhTransQueryTask {

    //#平台收取费率方式，0固定，1百分比
    @Value("${CUST_ID}")
    String CUST_ID;

    @Value("${USER_ID}")
    String USER_ID;

    @Value("${PASSWORD}")
    String PASSWORD;

    @Value("${ACCOUNT}")
    String ACCOUNT;

    @Value("${JH_CONNECT_URL}")
    String JH_CONNECT_URL;

    @Value("${SYS_JH_TRAN_URL}")
    String SYS_JH_TRAN_URL;

    @Value("${SIGN_KEY}")
    String SIGN_KEY;

    private Logger logger = Logger.getLogger(JhTransQueryTask.class);

//    @Scheduled(cron = "0 0/1 * * * ?")
    public void excuteQuery(){
        logger.info("建行定时查询开始");
        JhQueryParam jhQueryParam = new JhQueryParam(CUST_ID,USER_ID,PASSWORD,ACCOUNT);
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("requestXml", JhAccontDetail.buildReqXml(SeqNoUtil.nextSeqNo(),
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
                jhQueryParam.page,
                jhQueryParam.postStr,
                jhQueryParam.totalRecord,
                jhQueryParam.detNo));
        try {
            String res = HttpClient431Util.sendXmlData(JH_CONNECT_URL,paramMap);
            if (Strings.isNullOrEmpty(res)){
                logger.warn("建行查询信息返回为空！");
                return;
            }
            HttpClient431Util.doPost(buildParams(res),SYS_JH_TRAN_URL);
            logger.info("建行查询返回成功:"+res);
        } catch (Exception e) {
            logger.error("定时查询建行异常",e);
        }
    }

    private Map<String, String> buildParams(String res) throws Exception{
        String base = res + SIGN_KEY;
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
    String totalRecord = "200";//每页记录数
    String detNo = "";//活存账户明细号

    JhQueryParam (  String custId,
                    String userId,
                    String password,
                    String account ){
        beginDate = DateUtil.format(new Date(),"yyyyMMdd");//今日
        endDate = DateUtil.format(DateUtil.yesterday(),"yyyyMMdd");//昨日
        this.custId = custId;
        this.userId = userId;
        this.password = password;
        this.account = account;
    }

}
