package com.jhl.common.sms;

import com.jhl.constant.ConfigKey;
import com.jhl.service.SysConfig;
import com.jhl.util.HttpClient431Util;
import com.jhl.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hallywu on 16/3/7.
 */
public class JLSmsConnector extends BaseSmsConnector {

    private static Logger logger = LoggerFactory.getLogger(JLSmsConnector.class);

    @Override
    protected void excute(String mobile, String msg) throws Exception {
//        String gateWay = SysConfig.geteConfigByKey(ConfigKey.SMS_SUPPLIER_JL);
        String gateWay = "http://hy.junlongtech.com:8086/getsms";
        Map<String, String> nameValuePairs = new HashMap<String,String>();
        nameValuePairs.put("username",SysConfig.
                geteConfigByKey(ConfigKey.JL_USERID));
        nameValuePairs.put("password", MD5Util.getMD5String(SysConfig.
                geteConfigByKey(ConfigKey.JL_PWD)).toUpperCase());
        nameValuePairs.put("mobile",mobile);
        nameValuePairs.put("content",msg);
        nameValuePairs.put("extend",SysConfig.
                geteConfigByKey(ConfigKey.JL_EXTEND));
        nameValuePairs.put("level","1");
//        String result = "";
        String result = HttpClient431Util.doGet(nameValuePairs, gateWay);

        logger.info("短信接口："+gateWay+"，返回结果"+result);
    }
}
