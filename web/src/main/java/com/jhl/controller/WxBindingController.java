package com.jhl.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhl.dao.WechatDao;
import com.jhl.dto.AccountDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.AccountService;
import com.jhl.service.AchieveService;
import com.jhl.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/3/19.
 */
@Controller
@RequestMapping("wxBinding")
public class WxBindingController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(WxBindingController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    private AchieveService achieveService;
    @Autowired
    private WechatDao wechatDao;
    /**
     * 前往绑定页面
     */
    @RequestMapping(value = "toBinding/{openId}")
    public ModelAndView toBinding(@PathVariable("openId") String openId) {
        logger.info("跳转到微信端的登录页面");
        ModelAndView mv = new ModelAndView();
        mv.addObject("openId", openId);
        mv.addObject("url", "/wxBinding/binding");
        mv.setViewName("forward:/wxbinding.jsp");
        return mv;
    }

    /**
     * 去注册页面
     * @return
     */
    @RequestMapping(value = "toRegBinding/{openId}")
    public ModelAndView toRegBinding(@PathVariable("openId") String openId) {
        logger.info("跳转到微信端的注册页面");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forward:/login.jsp");
        mv.addObject("openId", openId);
        return mv;
    }

    /**
     * 登录绑定微信
     * @param dto
     */
    @RequestMapping(value = "binding")
    public @ResponseBody
    JsonBack Binding(AccountDto dto) {
        logger.info("登录的同时绑定微信");
        JsonBack jsonBack = new JsonBack();
        Account account = null;
        if(dto != null && dto.getOpenId() != null) {
            try {
                account = accountService.queryAccountByName(dto.getAccName());
                if (account == null) {
                    return buildErrorJsonBack(jsonBack,"*用户不存在，请先注册!");
                }
                if(!PasswordUtil.verify(dto.getPwd(),account.getPwd())) {
                    return buildErrorJsonBack(jsonBack,"*用户密码不正确!");
                }
                if (account.getOpenId() != null) {
                    return buildErrorJsonBack(jsonBack,"*账号已绑定微信，请填写其他账号!");
                }
                if(wechatDao.getAccountByOpenID(dto.getOpenId()) != null) {
                    return buildErrorJsonBack(jsonBack,"*当前微信号已被绑定!");
                }
                account.setOpenId(dto.getOpenId());
                accountService.setOpenIdToAccount(account);
                String token = accountService.excutePwdLogin(account,dto.getImei());
                return buildSuccJsonBack(jsonBack,token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return buildErrorJsonBack(jsonBack,"*请在微信浏览器上进行绑定操作。");
        }
        return jsonBack;
    }


    /**
     * 测试获取素材列表
     */
    @RequestMapping(value = "getImageTextList")
    public ModelAndView getImageTextList(String type, String offset, String count) {
        ModelAndView mv = new ModelAndView();
        System.out.println("调用素材列表接口:类型："+type+"; 偏移量："+offset+"; 数量："+count);
        JSONObject jsonObject = achieveService.getMediaList(type, offset,count);
        mv.addObject("msg", jsonObject);
        mv.setViewName("forward:/index.jsp");
        return mv;
    }

    /**
     * 测试获取素材
     */
    @RequestMapping(value = "getImageText")
    public void getImageTextList(String keyName, String mediaId) {
        System.out.println("调用获取永久素材接口： 素材ID："+mediaId);
        achieveService.getMediaById(keyName,mediaId);
    }
}
