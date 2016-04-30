package com.jhl.service;

import com.alibaba.fastjson.JSONObject;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.WechatDao;
import com.jhl.dto.ModelInfoDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.weixin.resp.TextMessage;
import com.jhl.util.DESUtil;
import com.jhl.util.DateUtil;
import com.jhl.util.MessageManager;
import com.jhl.util.MessageUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/18.
 */
@Service("wechatService")
public class WechatService {

    private static Logger logger = LoggerFactory.getLogger(WechatService.class);
    @Autowired
    private WechatDao wechatDao;
    @Autowired
    private AchieveService achieveService;
    /**
     * 处理微信服务器发送过来的请求
     */
    @SuppressWarnings({ "unused"})
    public String processRequest(HttpServletRequest request) {
        logger.info("微信服务器消息请求处理");

        String respMessage = null;
        try {
            Map<String, String> requestMap = parseXml(request);//解析微信发来的请求(xml)， 也就是将xml转化为实体类
            // 默认返回的文本消息内容
            String respContent = "";
            // 发送方帐号(open_id)
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 时间
            long createTime = new Date().getTime();

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(createTime);
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            //事件开始

            if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){//文本消息
                    return "";
            } else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){// 图片消息
                return "";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {// 地理位置消息
                return "";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {// 链接消息
                return "";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {// 音频消息
                return "";
            } else {
                if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                    //点击类型事件, 如果是自定义菜单的点击事件
                    String eventKey = requestMap.get("EventKey");
                    if(achieveService.getMenu("资讯中心", eventKey)) {
                        JSONObject jsonObject = achieveService.getImageText("资讯中心");
                        return MessageManager.getImageTextInfo(jsonObject, requestMap);
                    }
                    if(achieveService.getMenu("公司简介", eventKey)) {
                        JSONObject jsonObject = achieveService.getImageText("公司简介");
                        return MessageManager.getImageTextInfo(jsonObject, requestMap);
                    }
                    if(achieveService.getMenu("管理团队", eventKey)) {
                        JSONObject jsonObject = achieveService.getImageText("管理团队");
                        return MessageManager.getImageTextInfo(jsonObject, requestMap);
                    }
                    if(achieveService.getMenu("房产投资", eventKey)) {
                        JSONObject jsonObject = achieveService.getImageText("房产投资");
                        return MessageManager.getImageTextInfo(jsonObject, requestMap);
                    }
                    if(achieveService.getMenu("新手指南", eventKey)) {
                        JSONObject jsonObject = achieveService.getImageText("新手指南");
                        return MessageManager.getImageTextInfo(jsonObject, requestMap);
                    }
                    if(achieveService.getMenu("投资攻略", eventKey)) {
                        JSONObject jsonObject = achieveService.getImageText("投资攻略");
                        return MessageManager.getImageTextInfo(jsonObject, requestMap);
                    }
                    if (achieveService.getMenu("账户余额", eventKey)) {
                        Account account = wechatDao.getAccountByOpenID(fromUserName); //判断是否存在OPENID， 根据OPENID（fromUserName）查询用户是否存在
                        if (account != null) {
                            //存在，直接返回模板消息
                            ModelInfoDto dto = new ModelInfoDto();
                            String accName = account.getAccName();
                            Long money = account.getMoney();
                            dto.setFirst("您好！欢迎使用金葫芦,您的余额详情如下:");
                            dto.setKeyword1(DESUtil.getDesString(accName));
                            dto.setKeyword2((money/100)+"元");
                            dto.setKeyword3(DateUtil.format(new Date(), DateUtil.DATE_TIME_FORMAT2));
                            dto.setRemark("谢谢使用！");
                            achieveService.sendBalence(dto, fromUserName, null);
                            //respContent = "欢迎使用金葫芦!";
                            return "";
                        } else {
                            String httpUrl = SysConfig.geteConfigByKey(ConfigKey.WEB_GATEWAY);
                            //不存在，返回登录和注册的链接文本消息
                            respContent = "欢迎关注金葫芦！已有金葫芦账号，请<a href=\""+httpUrl+"wxBinding/toBinding/" + fromUserName + "\">点击这里</a>绑定";
                        }
                    }
                    if (achieveService.getMenu("账户绑定", eventKey)) {
                        Account account = wechatDao.getAccountByOpenID(fromUserName); //判断是否存在OPENID， 根据OPENID（fromUserName）查询用户是否存在
                        if (account != null) {
                            respContent = "您的微信已绑定金葫芦账户！";
                        } else {
                            String httpUrl = SysConfig.geteConfigByKey(ConfigKey.WEB_GATEWAY);
                            //不存在，返回登录和注册的链接文本消息
                            respContent = "欢迎关注金葫芦！已有金葫芦账号，请<a href=\""+httpUrl+"wxBinding/toBinding/" + fromUserName + "\">点击这里</a>绑定";
                        }
                    }
                }
            }
            // 回复文本消息
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch(Exception e) {
            //此处打印日志
            e.printStackTrace();
        }
        return respMessage;
    }



    /**
     * 解析微信服务器发送过来的xml请求
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }
}
