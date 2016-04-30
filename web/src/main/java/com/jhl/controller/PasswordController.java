package com.jhl.controller;

import com.google.common.base.Strings;
import com.jhl.common.constant.CommonConstant;

import com.jhl.constant.ConfigKey;
import com.jhl.pojo.config.Config;
import com.jhl.service.SysConfig;
import com.jhl.util.SessionUtil;
import com.jhl.dto.AccountDto;
import com.jhl.dto.PasswordDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.AchieveService;
import com.jhl.util.PasswordUtil;
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
 * Created by Administrator on 2016/2/18.
 * 密码API
 */
@Controller()
@RequestMapping("pwd")
public class PasswordController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);
    @Autowired
    private AchieveService achieveService;

    @RequestMapping(value = "auth/setLinePwd")
//    @RequestMapping(value = "auth/setLinePwd",method = RequestMethod.POST)
    public @ResponseBody JsonBack setLinePwd(AccountDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            account.setLinePwd(dto.getLinePwd());
            accountService.synUpdateAccount(account);
            return buildSuccJsonBack(jsonBack,"设置手势密码成功!");
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "修改登录密码异常！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 忘記密碼
     * @param dto
     * @return
     */
    @RequestMapping(value = "updateLoginPwd",method = RequestMethod.POST)
//    @RequestMapping(value = "updateLoginPwd")
    public @ResponseBody JsonBack updateLoginPwd(PasswordDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            if (smsService.checkCode(dto)){
                Account account = accountService.queryAccountByName(dto.getMobile());
                if (account == null) return buildErrorJsonBack(jsonBack,"手机号尚未注册，请先注册");
                if (!ValidateUtil.checkPwd(dto.getPwd())) return
                        buildErrorJsonBack(jsonBack,"密码格式错误");
                String pwd = PasswordUtil.generate(dto.getPwd());
                account.setPwd(pwd);
                account.parseAccout();
                accountService.synUpdateAccount(account);
                return buildSuccJsonBack(jsonBack,"登录密码修改成功，请重新登录");
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "修改登录密码异常！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 重置登录密码
     * @param dto
     * @return
     */
    @RequestMapping(value = "auth/resetLoginPwd",method = RequestMethod.POST)
//    @RequestMapping(value = "resetLoginPwd")
    public @ResponseBody JsonBack resetLoginPwd(PasswordDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            if (smsService.checkCode(dto)){
                Account session = SessionUtil.getSession();
                if (!ValidateUtil.checkPwd(dto.getPwd())) return
                        buildErrorJsonBack(jsonBack,"密码格式错误");
                String pwd = PasswordUtil.generate(dto.getPwd());
                session.setPwd(pwd);
                accountService.synUpdateAccount(session);
                return buildSuccJsonBack(jsonBack,"重置登录密码成功，请重新登录");
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "重置登录密码异常！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 修改交易密码
     * @param dto
     * @return
     */
    @RequestMapping(value = "auth/updateTradePwd",method = RequestMethod.POST)
    public @ResponseBody JsonBack updateTradePwd(PasswordDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            if (smsService.checkCode(dto)){
                if (!ValidateUtil.checkTradePwd(dto.getTradePwd())) return
                        buildErrorJsonBack(jsonBack,"交易密码格式错误");
                String pwd = PasswordUtil.generate(dto.getTradePwd());
                account.setTradePwd(pwd);
                accountService.synUpdateAccount(account);
                return buildSuccJsonBack(jsonBack,"交易密码设置成功");
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "修改登录密码异常！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 设置交易密码
     * @param dto
     * @return
     */
    @RequestMapping(value = "auth/setTradePwd",method = RequestMethod.POST)
    public @ResponseBody JsonBack setTradePwd(PasswordDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            if (!ValidateUtil.checkTradePwd(dto.getTradePwd())) return
                    buildErrorJsonBack(jsonBack,"交易密码格式错误");
            String pwd = PasswordUtil.generate(dto.getTradePwd());
            account.setTradePwd(pwd);
            account.setPrepareStep(Account.PrepareInvestStep.TRADE_PWD.getValue());
            accountService.synUpdateAccount(account);
            return buildSuccJsonBack(jsonBack,"交易密码设置成功");
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "修改登录密码异常！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    private void setAndAdd(int id) throws Exception{
        String  val = redisClient.getStr(ERROR_PREFIX+id);
        if (Strings.isNullOrEmpty(val)) {
            redisClient.set(ERROR_PREFIX+id,1);
        }else{
            Integer count = Integer.parseInt(val);
            redisClient.set(ERROR_PREFIX+id,count + 1);
        }
    }

    private boolean isOver(int id) throws Exception{
        Integer maxCount = Strings.isNullOrEmpty(SysConfig.geteConfigByKey(ConfigKey.MAX_TRADE_COUNT))?3:
                Integer.parseInt(SysConfig.geteConfigByKey(ConfigKey.MAX_TRADE_COUNT));
        String  val = redisClient.getStr(ERROR_PREFIX+id);
        if (Strings.isNullOrEmpty(val)) {
            setAndAdd(id);
            return false;
        }else{
            Integer count = Integer.parseInt(val);
            if(count > maxCount) {
                return true;
            }else{
                setAndAdd(id);
                return false;
            }
        }
    }

    private static final String ERROR_PREFIX = "trade_pwd_error_num_";
    @RequestMapping(value = "auth/ checkTradePwd",method = RequestMethod.POST)
    public @ResponseBody JsonBack checkTradePwd(PasswordDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            if (!ValidateUtil.checkTradePwd(dto.getTradePwd())) return
                    buildErrorJsonBack(jsonBack,"交易密码格式错误");

            if(PasswordUtil.verify(dto.getTradePwd(),account.getTradePwd())){
//                redisClient.del(ERROR_PREFIX + account.getId());
                return buildSuccJsonBack(jsonBack);
            }else {
                return buildErrorJsonBack(jsonBack);
//                if (isOver(account.getId())){
//                    redisClient.del(ERROR_PREFIX + account.getId());
//                    return buildErrorJsonBack(jsonBack,"555");//交易密码朝鲜
//                }else{
//                    setAndAdd(account.getId());
//                    return buildErrorJsonBack(jsonBack,redisClient.getStr(ERROR_PREFIX+account.getId()));
//                }
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "交易密码验证异常！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }
}

