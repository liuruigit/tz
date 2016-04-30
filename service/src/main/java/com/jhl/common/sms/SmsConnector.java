package com.jhl.common.sms;

import com.jhl.service.SysConfig;
import com.jhl.constant.ConfigKey;
import com.jhl.util.HttpClient431Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/17.
 */
public class SmsConnector extends BaseSmsConnector {

    private static Logger logger = LoggerFactory.getLogger(SmsConnector.class);

    @Override
    protected void excute(String mobile, String msg) throws Exception{
        String gateWay = SysConfig.geteConfigByKey(ConfigKey.SMS_SUPPLIER_MWKJ);
        Map<String, String> nameValuePairs = new HashMap<String,String>();
        nameValuePairs.put("userId","JC2428");
        nameValuePairs.put("password","226578");
        nameValuePairs.put("pszMobis",mobile);
        nameValuePairs.put("pszMsg",msg);
        nameValuePairs.put("iMobiCount","1");
        nameValuePairs.put("MsgId","0");
        String result = HttpClient431Util.doGet(nameValuePairs,gateWay);
        logger.info("短信接口："+gateWay+"，返回结果"+result);

    }
}
