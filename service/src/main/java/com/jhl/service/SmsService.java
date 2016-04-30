package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.cache.VerifyCodeCache;
import com.jhl.common.sms.BaseSmsConnector;
import com.jhl.common.sms.JLSmsConnector;
import com.jhl.dto.BaseDto;
import com.jhl.pojos.VerifyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hally on 2016/1/17.
 * 短信服务
 */
@Service("smsService")
public class SmsService {
    private Logger logger = LoggerFactory.getLogger(SmsService.class);
    @Autowired
    VerifyCodeCache verifyCodeCache;
    BaseSmsConnector smsConnector = new JLSmsConnector();

    /**
     * 通用短信服务，同一手机号以最后一条消息为准
     * @param mobile
     * @throws Exception
     */
    public String sendDefaultCode(String mobile) throws Exception{
        String code = BaseSmsConnector.genVerifyCode();
        VerifyCode verifyCode = new VerifyCode(mobile,code);
        smsConnector.sendCodeMsg(mobile,code);
        return verifyCodeCache.cacheCode(verifyCode);
    }

    /**
     * 注册短信服务
     * @param mobile
     * @throws Exception
     */
    public String sendRegistCode(String mobile) throws Exception{
        String code = BaseSmsConnector.genVerifyCode();
        VerifyCode verifyCode = new VerifyCode(mobile,code);
        smsConnector.sendCodeMsg(mobile,code);
        return verifyCodeCache.cacheCode(verifyCode);
    }

    /**
     * 根据模板发送短信
     * @param mobile
     * @throws Exception
     */
    public String sendCode(String mobile,String tempKey) throws Exception{
        String code = BaseSmsConnector.genVerifyCode();
        VerifyCode verifyCode = new VerifyCode(mobile,code);
        smsConnector.sendMsgByTemp(mobile, code, tempKey);
        return verifyCodeCache.cacheCode(verifyCode);
    }

    /**
     * 比对手机验证码
     * @param dto
     * @return
     * @throws Exception
     */
    public boolean checkCode(BaseDto dto) throws Exception{
        VerifyCode cache = verifyCodeCache.getCache(dto.getSmsNo());
        if (cache != null) {
            logger.info("cache:"+cache.toString());
        }
        String code = dto.getCode();
        return cache != null && code != null && code.equalsIgnoreCase(cache.getCode());

    }

    public boolean isProcessing(String smsNo) throws Exception {
        VerifyCode cache = verifyCodeCache.getCache(smsNo);
        if (cache != null) {
            logger.info("cache:"+cache.toString());
        }
        return cache != null && VerifyCode.PROCESSING_STATUS_Y.equalsIgnoreCase(cache.getProcessing());
    }

    public void clean(String smsNo) throws Exception {
        if (Strings.isNullOrEmpty(smsNo))return;
        VerifyCode cache = verifyCodeCache.getCache(smsNo);
        if (cache != null) {
            verifyCodeCache.del(smsNo);
        }
    }

    public void setProcessing(BaseDto dto) throws Exception{
        VerifyCode cache = verifyCodeCache.getCache(dto.getSmsNo());
        if (cache == null) {
            return;
        }
        cache.setProcessing(VerifyCode.PROCESSING_STATUS_Y);
        verifyCodeCache.set(dto.getSmsNo(),cache);
    }
}
