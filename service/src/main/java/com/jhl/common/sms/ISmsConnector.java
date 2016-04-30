package com.jhl.common.sms;

/**
 * Created by Administrator on 2016/1/17.
 * 短信接口
 */
public interface ISmsConnector {

    /**
     * 默认模板
     * 发送验证码
     * @throws Exception
     */
    public void sendCodeMsg(String mobile,String code) throws Exception;

    /**
     * 发送注册验证码
     * @param mobile
     * @throws Exception
     */
    public void sendRegistMsg(String mobile,String code) throws Exception;

    /**
     * 根据模板发送短信发送注册验证码
     * @param mobile
     * @throws Exception
     */
    public void sendMsgByTemp(String mobile,String code,String temp) throws Exception;

    /**
     * 发送系统短信
     * @param mobile
     * @param msg
     * @throws Exception
     */
    public void sendSysMsg(String mobile,String msg) throws Exception;
}
