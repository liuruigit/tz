package com.jhl.controller;

import com.jhl.service.WechatService;
import com.jhl.util.SignUtil;
import com.jhl.util.WxPublicNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/3/18.
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatService wechatService;
    /**
     * 微信服务器请求验证
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.info("微信服务器请求校验...");
        try {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");

            String token = WxPublicNo.token;
            PrintWriter out = null;
            out = response.getWriter();
            // 通过检验signature对请求进行校验,若校验成功则原样返回echostr,表示接入成功,否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce, token)) {
                logger.info("校验成功！"+echostr);
                out.print(echostr);
            }
            out.close();
            out = null;
        } catch (Exception e) {
            logger.error("微信服务器请求校验异常...", e);
            e.printStackTrace();
        }
    }

    /**
     * 微信服务器消息处理
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        logger.info("微信消息处理...");
        try {
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String respMessage = null;
            //消息请求处理
            respMessage = wechatService.processRequest(request);
            // 响应消息
            PrintWriter out = response.getWriter();
            out.print(respMessage);
            out.close();
        } catch (Exception e) {
            logger.error("微信消息处理异常...", e);
            e.printStackTrace();
        }
    }
}
