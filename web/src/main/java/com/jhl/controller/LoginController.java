package com.jhl.controller;

import com.google.common.base.Strings;
import com.jhl.common.constant.CommonConstant;
import com.jhl.util.SessionUtil;
import com.jhl.dto.AccountDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.AccountService;
import com.jhl.util.DESUtil;
import com.jhl.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/1/21.
 * 登录API
 */
@Controller
@RequestMapping("account")
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AccountService accountService;

    /**
     * 登录
     * @return
     */
    @RequestMapping("login")
    public @ResponseBody JsonBack login(AccountDto dto, HttpServletRequest request){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = accountService.queryAccountByName(dto.getAccName());
            if (account == null) {
                return buildErrorJsonBack(jsonBack,"用户不存在，请先注册。");
            }
            if(!PasswordUtil.verify(dto.getPwd(),account.getPwd())) {
                return buildErrorJsonBack(jsonBack,"用户密码不正确。");
            }
            String address = "";
            if (!Strings.isNullOrEmpty(dto.getClient()) || dto.getClient().equalsIgnoreCase(CommonConstant.CLIENT_WEB)){
                address = getAddress(request);
            }
         String token = accountService.excutePwdLogin(account,dto.getImei(),address);
            return buildSuccJsonBack(jsonBack,token);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "登录失败!",e);
        }
        return jsonBack;
    }

    /**
     * 手势密码登录
     * @return
     */
    @RequestMapping("lineLogin")
    public @ResponseBody JsonBack lineLogin(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = accountService.queryAccountByName(dto.getAccName());
            if (account == null) {
                return buildErrorJsonBack(jsonBack,"用户不存在，请先注册。");
            }
            if (Strings.isNullOrEmpty(dto.getLinePwd())) {
                return buildErrorJsonBack(jsonBack,"手势密码未设置，请先设置");
            }
            String linePwd = DESUtil.getEncString(dto.getLinePwd());
            if(!linePwd.equalsIgnoreCase(account.getLinePwd())) {
                return buildErrorJsonBack(jsonBack,"用户密码不正确。");
            }
            String token = accountService.excuteLineLogin(account,dto.getImei());
            return buildSuccJsonBack(jsonBack,token);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "手势密码登录失败!",e);
        }
        return jsonBack;
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("auth/logout")
    public @ResponseBody JsonBack logout(){
        JsonBack jsonBack = new JsonBack();
        try {
            accountService.logout(SessionUtil.getSession());
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "退出错误！",e);
        }
        return jsonBack;
    }

}
