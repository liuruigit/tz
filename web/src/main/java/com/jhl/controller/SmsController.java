package com.jhl.controller;

import com.jhl.common.constant.CommonConstant;
import com.jhl.util.SessionUtil;
import com.jhl.constant.ConfigKey;
import com.jhl.dto.AccountDto;
import com.jhl.dto.BaseDto;
import com.jhl.pojo.biz.Account;
import com.jhl.service.SmsService;
import com.jhl.util.ValidateUtil;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.BankCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Hally on 2016/1/17.
 * 短信API
 */
@Controller
@RequestMapping("sms")
public class SmsController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    SmsService smsService;
    @Autowired
    BankCardService bankCardService;

    /**
     * 发送注册验证码
     * @param dto
     * @return
     */
    @RequestMapping(value = "reg")
    public @ResponseBody JsonBack sendRegSms(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            synchronized ("sms"){
                if(accountService.isRepeat(dto.getMobile())){
                    String no = smsService.sendRegistCode(dto.getMobile());
                    return buildSuccJsonBack(jsonBack,no);
                }else {
                    jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
                    jsonBack.setMessage("该手机已经注册，请直接登录。");
                }
            }
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 发送默认验证码
     * @param mobile
     * @return
     */
    @RequestMapping(value = "default")
    public @ResponseBody JsonBack sendDefaultSms(String mobile){
        JsonBack jsonBack = new JsonBack();
        try {
            String no = smsService.sendDefaultCode(mobile);
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 发送默认验证码
     * @param dto
     * @return
     */
    @RequestMapping(value = "auth/default")
    public @ResponseBody JsonBack sendLogin(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            String no = smsService.sendDefaultCode(account.getMobile());
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 投资支付验证码
     * @return
     */
    @RequestMapping(value = "auth/invest")
    public @ResponseBody JsonBack sendInvestSms(){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            if (account.getPrepareStep() != Account.PrepareInvestStep.BIND_CARD.getValue())
                return buildErrorJsonBack(jsonBack,"请先完成投资前准备！");
//            BankCard bankCard = bankCardService.queryDefaultBank(account.getId());
            String no = smsService.sendCode(account.getMobile(), ConfigKey.SMS_INVEST);
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 发送充值验证码，需用户登录
     * @return
     */
    @RequestMapping(value = "auth/charge")
    public @ResponseBody JsonBack sendChargeSms(){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            BankCard bankCard = bankCardService.queryDefaultBank(account.getId());
            if (bankCard == null){
                logger.error("充值短信发送失败，绑定银行卡不存在：{}",account.toString());
                return buildErrorJsonBack(jsonBack);
            }
            String no = smsService.sendCode(bankCard.getMobile(), ConfigKey.SMS_CHARGE);
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }


    /**
     * 发送提现验证码，需用户登录
     * @return
     */
    @RequestMapping(value = "auth/cash")
    public @ResponseBody JsonBack sendCashSms(){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            BankCard bankCard = bankCardService.queryDefaultBank(account.getId());
            if (bankCard == null){
                logger.error("充值短信发送失败，绑定银行卡不存在：{}",account.toString());
            }
            String no = smsService.sendCode(bankCard.getMobile(), ConfigKey.SMS_CASH);
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 发送重置密码验证码
     * @return
     */
    @RequestMapping(value = "auth/resetPwd")
    public @ResponseBody JsonBack resetPwd(AccountDto accountDto){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            if(!ValidateUtil.checkMobile(account.getMobile()))return buildErrorJsonBack(jsonBack,"手机号码格式不正确！");
            if(accountService.queryAccountByName(account.getMobile()) == null)return buildErrorJsonBack(jsonBack,"输入的手机号未注册！");
            String no = smsService.sendCode(account.getMobile(), ConfigKey.SMS_PWD);
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 发送重置消费密码验证码
     * @return
     */
    @RequestMapping(value = "auth/resetTradePwd")
    public @ResponseBody JsonBack resetTradePwd(){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            String no = smsService.sendCode(account.getMobile(), ConfigKey.SMS_TRADE_PWD);
            return buildSuccJsonBack(jsonBack,no);
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "发送短信验证码失败！", e);
        }
        return jsonBack;
    }

    /**
     * 校验短信验证码
     * @param dto
     * @return
     */
    @RequestMapping(value = "check")
    public @ResponseBody JsonBack check(BaseDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            if (smsService.checkCode(dto)){
                return buildSuccJsonBack(jsonBack);
            }else {
                return buildErrorJsonBack(jsonBack,"短信验证码错误");
            }
        } catch (Exception e) {
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            logger.error(SessionUtil.getNo() + "短信验证码校验异常！", e);
        }
        return jsonBack;
    }
}
