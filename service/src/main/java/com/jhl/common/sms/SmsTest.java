package com.jhl.common.sms;

import com.jhl.util.HttpClient431Util;
import com.jhl.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hallywu on 16/3/7.
 * 君隆科技
 */
public class SmsTest {

    public static void main(String[] args) throws Exception {
        String gateWay = "http://hy.junlongtech.com:8086/getsms";
        Map<String, String> nameValuePairs = new HashMap<String,String>();
        nameValuePairs.put("username","test001");
        nameValuePairs.put("password", MD5Util.getMD5String("testtest").toUpperCase());
        nameValuePairs.put("mobile","13148899351");
        nameValuePairs.put("content","短信QQ人才123455");
        nameValuePairs.put("extend","0053");
        nameValuePairs.put("level","1");
        String result = HttpClient431Util.doGet(nameValuePairs, gateWay);
        System.out.println(("短信接口："+gateWay+"，返回结果"+result));
    }

}
