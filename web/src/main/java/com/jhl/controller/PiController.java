package com.jhl.controller;

import com.jhl.util.SessionUtil;
import com.jhl.constant.SystemConstant;
import com.jhl.dto.AccountDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.UnbindingApply;
import com.jhl.pojo.json.JsonBack;
import com.jhl.proxy.IProxy;
import com.jhl.service.BankCardService;
import com.jhl.service.ChannelOrderService;
import com.jhl.service.UnBindingService;
import com.jhl.util.DateUtil;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 * 银行卡相关API
 */
@Controller
@RequestMapping("auth/pi")
public class PiController extends BaseController{

    @Autowired
    IProxy proxy;
    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    BankCardService bankCardService;
    @Autowired
    ChannelOrderService channelOrderService;
    @Autowired
    UnBindingService unBindingService;

    /**
     * 通过银行卡实名认证，认证完成后绑定银行卡
     * @param dto
     * @param bankCard
     * @return
     */
    @RequestMapping(value = "binding")
    public @ResponseBody
    JsonBack binding(AccountDto dto, BankCard bankCard){
        JsonBack jsonBack = new JsonBack();

        try {
            if(smsService.checkCode(dto)){//先对手机号码进行鉴权
                Account session = SessionUtil.getSession();
                if (bankCardService.queryDefaultBank(session.getId()) != null)return buildErrorJsonBack(jsonBack,"您已绑定银行卡，无法绑定新卡");
                if (bankCardService.queryCardByNo(bankCard.getBankCardNo()) != null)return buildErrorJsonBack(jsonBack,"银行卡已绑定其他账户");
                Map<String,String> result = proxy.bindingBankCard(bankCard,session,dto.getClient());
                if(IProxy.JYT_RESP_CODE_SUCCESS.equalsIgnoreCase(result.get("result"))){
                    return buildSuccJsonBack(jsonBack,result);
                }else {
                    return buildErrorJsonBack(jsonBack,result.get("result"));
                }
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "绑定银行卡异常: ", e);
        }
        return buildErrorJsonBack(jsonBack,"验证码错误！");
    }

    private static final String unbinding = "unbinding";
    @RequestMapping(value = "unbinding")
    public @ResponseBody
    JsonBack unbinding(HttpServletRequest request){
        JsonBack jsonBack = new JsonBack();
        try {
//            if(verifyCodeCache.verifyCode(bankCard.getMobile(), ConfigKey.SMS_DEFAULT, dto.getCode())){//先对手机号码进行鉴权
            Account session = SessionUtil.getSession();
            int checkResult = unBindingService.unBindingCheck(session);
            switch (checkResult) {
                case UnBindingService.CHECK_AGREE:
                    List<String> filePaths = fileUpload(request,unbinding);
                    if (filePaths.isEmpty()){
                        logger.error(SessionUtil.getNo() + "用户:{}解绑银行卡，上传附件失败！",session.getId());
                        return buildErrorJsonBack(jsonBack);
                    }
                    unBindingService.save(buildUnbinding(filePaths,session));
                    return buildSuccJsonBack(jsonBack,"解绑申请提交成功，请耐心等待人工审核结果");
                case UnBindingService.CHECK_ERROR_CASH:
                    return buildErrorJsonBack(jsonBack,"您有未到账的提现申请，请到账后再次尝试解绑");
                case UnBindingService.CHECK_ERROR_CHARGE:
                    return buildErrorJsonBack(jsonBack,"您有未到账的充值申请，请到账后再次尝试解绑");
                case UnBindingService.CHECK_ERROR_INVEST:
                    return buildErrorJsonBack(jsonBack,"您有未结算的投资记录，请结算后再次尝试解绑");
                default:
                    logger.error(SessionUtil.getNo() + "{}系统出现无法匹配的解绑申请，请检查:{}", SystemConstant.LOG_ERROR,checkResult);
                    return buildErrorJsonBack(jsonBack,"解绑申请失败");
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "{}解绑申请失败异常: ",SystemConstant.LOG_ERROR,e);
        }
        return buildErrorJsonBack(jsonBack,"解绑申请提交失败！");
    }

    private UnbindingApply buildUnbinding(List<String> filePath, Account account){
        UnbindingApply unBindingApply = new UnbindingApply();
        StrBuilder buffer = new StrBuilder();
        for(String path : filePath) {
            buffer.append(path).append("@");
        }
        unBindingApply.setAttach(buffer.toString());
        unBindingApply.setAccId(account.getId());
        unBindingApply.setCreateTime(DateUtil.now());
        unBindingApply.setUpdateTime(DateUtil.now());
        unBindingApply.setStatus(0);
        return unBindingApply;
    }
}
