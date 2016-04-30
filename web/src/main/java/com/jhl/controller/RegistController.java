package com.jhl.controller;

import com.google.common.base.Strings;
import com.jhl.common.constant.CommonConstant;
import com.jhl.dto.AccountDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.AccountService;
import com.jhl.service.SmsService;
import com.jhl.util.SessionUtil;
import com.jhl.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/1/17.
 */
@Controller
@RequestMapping("account")
public class RegistController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(RegistController.class);

    @Autowired
    AccountService accountService;
    @Autowired
    SmsService smsService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
//    @RequestMapping(value = "add")
    public @ResponseBody JsonBack add(AccountDto accountDto) {
        JsonBack jsonBack = new JsonBack();
//        System.out.println("注册参数：" + accountDto.toString());
        try {
//            if (smsService.isProcessing(accountDto.getSmsNo()))return buildErrorJsonBack(jsonBack,"处理中，请勿重复提交");
            if(!smsService.checkCode(accountDto)){
                return buildErrorJsonBack(jsonBack,"短信验证码输入有误");
            }

            if(!Strings.isNullOrEmpty(accountDto.getRecommendMobile())){
                if (!ValidateUtil.checkMobile(accountDto.getRecommendMobile())){
                    return buildErrorJsonBack(jsonBack,"推荐码格式错误");
                }
                if (accountService.queryAccountByName(accountDto.getRecommendMobile()) == null){
                    return buildErrorJsonBack(jsonBack,"推荐人尚未注册");
                }
            }

            if (accountService.queryAccountByName(accountDto.getAccName()) != null) {
                jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
                jsonBack.setMessage("手机号:"+accountDto.getAccName()+"已注册");
                return jsonBack;
            }
            if (!accountService.saveCheck(accountDto.getAccName(),accountDto.getPwd())) {
                jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
                jsonBack.setMessage("非法的请求数据！");
                return jsonBack;
            }
            synchronized ("lock"){
                Account account = new Account();
                account.setPwd(accountDto.getPwd());
                account.setAccName(accountDto.getAccName());
                account.setRecommendId(getRecommendId(accountDto.getRecommendMobile()));
                Account result = accountService.saveAccount(account);
                String token = accountService.excutePwdLogin(result,accountDto.getImei());
                jsonBack.setObj(token);
                jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
                smsService.clean(accountDto.getSmsNo());
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "增加用户出现异常", e);
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
            jsonBack.setMessage("注册失败！");
        }
        return jsonBack;
    }

    /**
     * 检查用户是否存在
     * @param accountDto
     * @return
     */
    @RequestMapping(value = "isExists",method = RequestMethod.POST)
//    @RequestMapping(value = "add")
    public @ResponseBody JsonBack isExists(AccountDto accountDto) {
        JsonBack jsonBack = new JsonBack();
        try {
           if (accountService.queryAccountByName(accountDto.getAccName()) == null) {
               return buildErrorJsonBack(jsonBack,"该用户不存在，请注册");
           }else{
               return buildSuccJsonBack(jsonBack);
           }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "增加用户出现异常", e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    private Integer getRecommendId(String recommend) throws Exception {
        if (Strings.isNullOrEmpty(recommend)){
            return 0;
        }else{
            //定了推荐码规则，需要先校验，格式不正确，不执行查询
            Account account = accountService.queryAccountByName(recommend);
            return account == null ? 0 : account.getId();
        }
    }
}
