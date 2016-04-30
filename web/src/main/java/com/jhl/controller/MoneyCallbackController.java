package com.jhl.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.jhl.constant.ConfigKey;
import com.jhl.dto.JhDto;
import com.jhl.pojo.json.JsonBack;
import com.jhl.proxy.ICallback;
import com.jhl.service.SysConfig;
import com.jhl.util.MD5Util;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hallywu on 16/1/29.
 * 资金托管渠道回调API
 */
@Controller
@RequestMapping("pay")
public class MoneyCallbackController extends BaseController {

    @Autowired
    ICallback callback;
    private static final Logger logger = LoggerFactory.getLogger(MoneyCallbackController.class);

    @RequestMapping("callback/charge")
    public void callbackCharge(HttpServletRequest request, HttpServletResponse response){
        try {
            JSONObject jsonObject = callback.parseReqParameter(request);
            callback.callback(jsonObject);
            response.getWriter().println("response success");
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "资管渠道回调处理失败！",e);
        }
    }

    @RequestMapping("jhTransaction")
    public @ResponseBody JsonBack jhTransaction(JhDto jhDto){
        JsonBack jsonBack = new JsonBack();
        logger.info("建行充值转账处理开始：{}", jhDto.toString());
        try {
            if (check(jhDto)){
                callback.jhTransaction(jhDto.getRes());
                return buildSuccJsonBack(jsonBack,"转账处理成功");
            }else {
                logger.warn("jh回调验证签名失败：{}",jhDto.toString());
                return buildErrorJsonBack(jsonBack,"处理失败");
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "资管渠道回调处理失败！",e);
        }
        return buildErrorJsonBack(jsonBack,"处理失败");
    }

//    @RequestMapping("jslwnUsjwr")
//    public @ResponseBody JsonBack jhTransactionTest(JhDto jhDto){
//        JsonBack jsonBack = new JsonBack();
//        try {
//            callback.jhTransaction(jhDto.getRes());
//            return buildSuccJsonBack(jsonBack,"转账处理成功");
//        } catch (Exception e) {
//            logger.error(SessionUtil.getNo() + "资管渠道回调处理失败！",e);
//            return buildErrorJsonBack(jsonBack,e.getLocalizedMessage());
//        }
//    }

    private boolean check(JhDto jhDto) throws Exception{
        if (Strings.isNullOrEmpty(jhDto.getRes()) || Strings.isNullOrEmpty(jhDto.getSign()))return false;
        String base = jhDto.getRes() + SysConfig.geteConfigByKey(ConfigKey.SECRET_KEY);
        return jhDto.getSign().equalsIgnoreCase(MD5Util.getMD5String(base));
    }
}
