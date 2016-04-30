package com.jhl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhl.common.constant.CommonConstant;
import com.jhl.util.SessionUtil;
import com.jhl.pojo.biz.Account;
import com.jhl.security.JwtHolder;
import com.jhl.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vic wu on 2015/8/26.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Resource(name = "webBaseController")
    BaseController base;

    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("******Session拦截器{}:{}",SessionUtil.getNo(),request.getRequestURI());
        String token = request.getParameter("token");
        String imei = request.getParameter("imei");
        if (StringUtils.isBlank(token) || StringUtils.isBlank(imei)) {
            writeResponseNoLogin(response);
            return false;
        }else{
            try {
                Account account = JwtHolder._instance().verifyToken(token);
                if (account == null){
                    writeResponseNoLogin(response);
                    return false;
                }
                SessionUtil.setSession(account);
            }catch (Exception e){
                JSON json = new JSONObject(initResultMap());
                logger.warn("鉴权API失败",e);
                writeResponse(response, json.toJSONString());
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    private void writeResponseNoLogin(HttpServletResponse response){
        JSON json = new JSONObject(initResultMap());
        writeResponse(response,json.toJSONString());
    }

    private Map<String, Object> initResultMap(int code){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message","");
        map.put("obj",code);
        return map;
    }

    private Map<String, Object> initResultMap(){
        return initResultMap(CommonConstant.JSON_BACK_NO_AUTH);
    }

    private void writeResponse(HttpServletResponse response,String string){
        try {
            response.getWriter().println(string);
            response.setStatus(200);
        } catch (IOException e) {
            logger.error(SessionUtil.getNo() + "登录拦截器异常!",e);
        }
    }
}
