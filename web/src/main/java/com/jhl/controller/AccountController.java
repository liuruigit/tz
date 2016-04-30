package com.jhl.controller;

import com.google.common.base.Strings;
import com.jhl.common.constant.CommonConstant;
import com.jhl.dto.AccountDto;
import com.jhl.dto.InvestDto;
import com.jhl.dto.PayDto;
import com.jhl.event.OnChargeSuccess;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.pojo.json.JsonBack;
import com.jhl.pojos.PageInfo;
import com.jhl.proxy.IProxy;
import com.jhl.service.*;
import com.jhl.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/26.
 * 用户API，均需要鉴权
 */
@Controller()
@RequestMapping("auth/account")
public class AccountController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    IProxy proxy;
    @Autowired
    BankCardService bankCardService;
    @Autowired
    ContractGenerateService contractGenerateService;
    @Autowired
    ChannelOrderService channelOrderService;
    @Autowired
    SecurityQuestionService securityQuestionService;
    @Autowired
    MessageService messageService;

    /**
     * 葫芦币明细
     * @param dto
     * @return
     */
    @RequestMapping("queryHlbDetail")
    public @ResponseBody
    JsonBack queryHlbDetail(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Map<String,String> result = new HashMap<String,String>();
            result.put("frozenCount","100");
            result.put("activeCOunt","20");
            result.put("outDateCount","30");
            result.put("outDate","2014-01-09");
            return buildSuccJsonBack(jsonBack,result);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "查询投资券问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 查询投资券接口
     * @param dto
     * @return
     */
    @RequestMapping("queryTotalCoupon")
    public @ResponseBody
    JsonBack queryTotalCoupon(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Map<String,String> result = new HashMap<String,String>();
            result.put("frozenCount","100");
            result.put("activeCOunt","20");
            result.put("outDateCount","30");
            result.put("outDate","2014-01-09");
            return buildSuccJsonBack(jsonBack,result);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "查询投资券问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 查询葫芦币接口
     * @param dto
     * @return
     */
    @RequestMapping("queryTotalHlb")
    public @ResponseBody
    JsonBack queryTotalHlb(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            Map<String,Object> hlb = new HashMap<String,Object>();
            hlb.put("activeCOunt",account.getPoint());
            hlb.put("frozenCount",account.getFrozenPoint());
            hlb.put("outDateCount",40);
            hlb.put("outDate", DateUtil.formatNow());
            return buildSuccJsonBack(jsonBack,hlb);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "葫芦币明细",e);
        }
        return buildErrorJsonBack(jsonBack);

    }

    /**
     * 设置消息已读
     * @param dto
     * @returnp
     */
    @RequestMapping("setMsgToRead")
    public @ResponseBody
    JsonBack setMsgToRead(AccountDto dto,String id){
        JsonBack jsonBack = new JsonBack();
        try{
            accountService.setMsgToRead(parseId(id));
            return buildSuccJsonBack(jsonBack);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "设置消息已读",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 比对安全问题
     * @param dto
     * @returnp
     */
    private int temp = 1;
    @RequestMapping("checkQuest")
    public @ResponseBody
    JsonBack checkQuest(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account session = SessionUtil.getSession();
            if(securityQuestionService.checkQues(session,dto)){
                return buildSuccJsonBack(jsonBack);
            }else{
                return buildErrorJsonBack(jsonBack);
            }
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "比对安全问题异常",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 查询用户设置的安全问题接口
     * @param dto
     * @return
     */
    @RequestMapping("queryAccQuestion")
    public @ResponseBody
    JsonBack queryAccQuestion(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            return buildSuccJsonBack(jsonBack,securityQuestionService.queryQuestion(account));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "查询安全问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 设置安全保护问题
     * @param dto
     * @return
     */
    @RequestMapping("setQuestion")
    public @ResponseBody
    JsonBack setQuestion(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            securityQuestionService.addQuestion(dto,account);
            return buildSuccJsonBack(jsonBack);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "设置安全问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 更新手机号
     * @param dto
     * @return
     */
    @RequestMapping("updateMobile")
    public @ResponseBody
    JsonBack updateMobile(AccountDto dto){
         JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            if (accountService.queryAccountByName(dto.getMobile()) != null){
                return buildErrorJsonBack(jsonBack,"该手机号已注册，不可再绑定！");
            }
            if (!smsService.checkCode(dto)){
                return buildErrorJsonBack(jsonBack,"短信验证码输入错误");
            }
            if (Strings.isNullOrEmpty(dto.getCertNo()) || !dto.getCertNo().equalsIgnoreCase(account.getCertNo())){
                return buildErrorJsonBack(jsonBack,"身份证不匹配，无法修改");
            }
            account.setMobile(dto.getMobile());
            account.setAccName(dto.getMobile());
            accountService.synUpdateAccount(account);
            return buildSuccJsonBack(jsonBack);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "更新手机号",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 我的投资券
     * @return
     */
    @RequestMapping("coupon")
    public @ResponseBody
    JsonBack coupon(AccountDto accountDto){
        JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            PageInfo pageInfo = getPageInfo(accountDto.getPage());
            return buildSuccJsonBack(jsonBack,accountService.queryAccCoupon(pageInfo,account));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "我的投资券查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 我的财富
     * @return
     */
    @RequestMapping("info")
    public @ResponseBody
    JsonBack info(){
        JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            return buildSuccJsonBack(jsonBack,accountService.getAccInfo(account));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "我的财富查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 我的消息
     * @return
     */
    @RequestMapping("msg")
    public @ResponseBody
    JsonBack msg(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            PageInfo pageInfo = getPageInfo(dto.getPage());
            return buildSuccJsonBack(jsonBack,accountService.queryMsg(pageInfo,account));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "我的消息查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 用户投资
     * @param dto 项目(标)ID
     * @return
     */
    @RequestMapping("invest")
    public @ResponseBody JsonBack invest(InvestDto dto){
        JsonBack jsonBack = new JsonBack();

        try{
//            if (smsService.isProcessing(dto.getSmsNo()))return buildErrorJsonBack(jsonBack,"处理中，请勿重复提交");
            Account session = SessionUtil.getSession();
            session.encrptAccout();
            if (session.getPrepareStep() != Account.PrepareInvestStep.BIND_CARD.getValue())
                return buildErrorJsonBack(jsonBack,"请先完成投资前准备");
            if (!smsService.checkCode(dto)){
                return buildErrorJsonBack(jsonBack,"短信验证码输入错误");
            }
            jsonBack = checkWithFigerPwd(dto,session,jsonBack);
            if (jsonBack.getCode() != CommonConstant.JSON_BACK_SUCCESS) return jsonBack;
            double amount = Double.parseDouble(dto.getAmount());
            int  projectId = parseId(dto.getProjectId());
            accountService.doInvest(session.getId(), session.getAccName(),projectId
                    ,amount,dto.getVersion(),dto.getProName(),dto.getCouponId());
            smsService.clean(dto.getSmsNo());
            return buildSuccJsonBack(jsonBack);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "投资失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 用户投资短信验证码校验
     * @param dto 项目(标)ID
     * @return
     */
    @RequestMapping("auth/investCodeCheck")
    public @ResponseBody JsonBack investCodeCheck(InvestDto dto){
        JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            if (smsService.checkCode(dto)){
                return buildSuccJsonBack(jsonBack);
            }else{
                return buildErrorJsonBack(jsonBack,"短信验证码输入错误！");
            }
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "投资短信验证码校验失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 通过银行卡实名认证，认证完成后绑定银行卡
     * @param dto        银行卡号
     */
    @RequestMapping(value = "validate")
    public @ResponseBody JsonBack validate(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            Account session = SessionUtil.getSession();
            if (session.getPrepareStep()>0)return buildErrorJsonBack(jsonBack,"您已完成投资前准备");
            if (!ValidateUtil.checkIDCard(dto.getCertNo()))return buildErrorJsonBack(jsonBack,"身份证输入格式错误");
            if (Strings.isNullOrEmpty(dto.getRealName()) || dto.getRealName().length() > 20)return buildErrorJsonBack(jsonBack,"真实姓名格式错误");
            if (accountService.queryAccByIdNo(dto.getCertNo()) != null)return buildErrorJsonBack(jsonBack,"身份证号已绑定");
            String result = proxy.validateAccIdNo(dto);
            if(IProxy.JYT_RESP_CODE_SUCCESS.equalsIgnoreCase(result)) {//发起实名认证
                session.setRealName( StringUtil.blankAndTapFilter(dto.getRealName()));
                session.setCertNo(StringUtil.blankAndTapFilter(dto.getCertNo()));
                session.setPrepareStep(Account.PrepareInvestStep.VALIDATE_REALNAME.getValue());
                accountService.synUpdateAccount(session);//更新Session数据
                return buildSuccJsonBack(jsonBack);
            }else {
                return buildErrorJsonBack(jsonBack,result);
            }

        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "实名认证异常: ", e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 充值
     * @param dto
     * @return
     */
    @RequestMapping(value = "charge")
    public @ResponseBody JsonBack charge(PayDto dto ){
        JsonBack jsonBack = new JsonBack();
        try {
//            if (smsService.isProcessing(dto.getSmsNo()))return buildErrorJsonBack(jsonBack,"处理中，请勿重复提交");
            Double amount = Double.parseDouble(dto.getAmount());
            Account session = SessionUtil.getSession();
            if (session.getPrepareStep() != Account.PrepareInvestStep.BIND_CARD.getValue())return buildErrorJsonBack(jsonBack,"请先完成投资前准备");
            BankCard bankCard = bankCardService.queryDefaultBank(session.getId());
            if (!bankCardService.checkBankRule(amount,bankCard)){
                logger.info("充值渠道检查失败:{}",bankCard);
                return buildErrorJsonBack(jsonBack,"充值金额超过限制！");
            }
            jsonBack = checkWithFigerPwd(dto,session,jsonBack);
            if (jsonBack.getCode() != CommonConstant.JSON_BACK_SUCCESS) return jsonBack;
            if(!smsService.checkCode(dto)){
                return buildErrorJsonBack(jsonBack,"短信验证码错误！");
            }
            Map<String,String> result = proxy.charge(bankCard, amount, session, new OnChargeSuccess() {
                @Override
                public void process(Double money, Account account) throws Exception {
                    contractGenerateService.asyGenWithHoldingContract(account,bankCard,amount);
                }
            });
            smsService.clean(dto.getSmsNo());
            if(result != null && result.size() > 0 && IProxy.JYT_RESP_CODE_SUCCESS.equals(result.get("respCode"))) {
                return buildSuccJsonBack(jsonBack);
            }else {
                return buildErrorJsonBack(jsonBack,result == null ? "" : result.get("respDesc"));
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "充值异常: ", e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 提现
     * @param dto
     * @return
     */
    @RequestMapping(value = "cash")
    public @ResponseBody JsonBack cash(PayDto dto){
        logger.info("提现申请开始:{}",dto.toString());
        JsonBack jsonBack = new JsonBack();
        try {
//            if (smsService.isProcessing(dto.getSmsNo()))return buildErrorJsonBack(jsonBack,"处理中，请勿重复提交");
            Double amount = Double.parseDouble(dto.getAmount());
            Account session = SessionUtil.getSession();
            if (session.getPrepareStep() != Account.PrepareInvestStep.BIND_CARD.getValue())return buildErrorJsonBack(jsonBack,"请先完成投资前准备");
            jsonBack = checkWithFigerPwd(dto,session,jsonBack);
            if (jsonBack.getCode() != CommonConstant.JSON_BACK_SUCCESS) return jsonBack;
            if(new Money(amount).getCent() > session.getMoney()){
                logger.warn("提现申请金额：{},{}",new Money(amount).getCent(),session.getMoney());
                return buildErrorJsonBack(jsonBack,"提现金额不能大于余额");
            }
            logger.info("Session数据：{}",session.toString());
            logger.info("短信校验：{}",smsService.checkCode(dto));
            logger.info("支付密码校验：{}",PasswordUtil.verify(dto.getTradePwd(),session.getTradePwd()));
            if(smsService.checkCode(dto)){//手机号码进行鉴权
                channelOrderService.save(session.getId(),amount, ChannelOrder.Type.CASH,"");
//                accountService.synUpdateAccount(session);//更新Session数据
                accountService.updateSession(session);
                logger.info("提现申请完成");
                messageService.sendCashApply(amount, "申请中", session);
                smsService.clean(dto.getSmsNo());
                return buildSuccJsonBack(jsonBack);
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "提现异常: ", e);
        }
            return buildErrorJsonBack(jsonBack,"提现申请失败！");
    }
}
