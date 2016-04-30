package com.jhl.common.sms;

import com.jhl.constant.ConfigKey;
import com.jhl.service.SysConfig;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Created by Administrator on 2016/1/17.
 */
public abstract class BaseSmsConnector implements ISmsConnector {

    private Logger logger = Logger.getLogger(BaseSmsConnector.class);
    protected static String channelName;
    protected static String channelPws;

    /**
     * 初始化
     * @throws Exception
     */
    public static void init() throws Exception {
        channelName = SysConfig.geteConfigByKey(ConfigKey.MWKJ_USERID);
        channelPws = SysConfig.geteConfigByKey(ConfigKey.MWKJ_PWD);
    }

    /**
     * 生成6位验证码
     * @return
     */
    public static String genVerifyCode(){
        Random random = new Random();
        int ran = random.nextInt(899999)+100000;
        System.out.println(ran);
        return ran + "";
    }

    /**
     * 生成CacheKey
     * @param mobile
     * @param temp
     * @return
     */
    public static String genKey(String mobile,String temp){
        return mobile + temp;
    }

    /**
     * 生成注册CacheKey
     * @param mobile
     * @return
     */
    public static String genRegistKey(String mobile){
        return genKey(mobile,ConfigKey.SMS_REGIST);
    }

    /**
     * 参数替换
     * @param temlete
     * @param pras
     * @return
     */
    public static String buildSms(String temlete,Object... pras) throws Exception{
        int pos = 0;
        for(Object para : pras) {
            String temp = "{"+pos+"}";
            if(temlete.contains(temp)){
                if (para == null) para = "";
                temlete = temlete.replace(temp,para.toString());
            }else {
                continue;
            }
            pos++;
        }
        return temlete;
    }

    @Override
    public void sendCodeMsg(String mobile,String code) throws Exception {
        String digestMsg = buildSms(SysConfig.geteConfigByKey(ConfigKey.SMS_DEFAULT),code);
        excute(mobile, digestMsg);
    }

    @Override
    public void sendRegistMsg(String mobile,String code) throws Exception {
        String digestMsg = buildSms(SysConfig.geteConfigByKey(ConfigKey.SMS_REGIST),code);
        excute(mobile,digestMsg);
    }

    @Override
    public void sendMsgByTemp(String mobile, String code, String temp) throws Exception {
        String digestMsg = buildSms(SysConfig.geteConfigByKey(temp),code);
        excute(mobile,digestMsg);
    }

    @Override
    public void sendSysMsg(String mobile, String msg) throws Exception {
        excute(mobile,msg);
    }

    /**
     * 消息发送
     * @param mobile
     * @param msg
     * @throws Exception
     */
    protected abstract void excute(String mobile,String msg)throws Exception;
}
