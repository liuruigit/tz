package com.jhl.service;

import com.alibaba.fastjson.JSONObject;
import com.jhl.dao.WechatDao;
import com.jhl.dto.MediaDto;
import com.jhl.dto.MenuDto;
import com.jhl.dto.ModelInfoDto;
import com.jhl.pojo.weixin.function.AccessToken;
import com.jhl.pojo.weixin.function.Template;
import com.jhl.pojo.weixin.function.TemplateData;
import com.jhl.util.HttpClient431Util;
import com.jhl.util.WeixinUtil;
import com.jhl.util.WxPublicNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信接口请求
 * Created by Administrator on 2016/3/18.
 */
@Service("achieveService")
public class AchieveService {
    private static Logger logger = LoggerFactory.getLogger(AchieveService.class);

    @Autowired
    private WechatDao wechatDao;


    /**
     * 获取图文消息列表, 仅管理员调用
     * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count 返回素材的数量，取值在1到20之间
     */
    public JSONObject getMediaList(String type, String offset, String count) {
        AccessToken accessToken = getAccessToken(WxPublicNo.appId, WxPublicNo.appSecret);
        JSONObject params = new JSONObject();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        String requestUrl = WxPublicNo.material_list_url.replace("ACCESS_TOKEN", accessToken.getToken());
        String result = null;
        try {
            result = HttpClient431Util.doPost(params, requestUrl, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取永久素材列表   清除以前保存到redis中的素材，同时将新的素材保存到redis中
     * @param mediaId
     */
    public void getMediaById(String keyName, String mediaId) {
        AccessToken accessToken = getAccessToken(WxPublicNo.appId, WxPublicNo.appSecret);
        JSONObject params = new JSONObject();
        params.put("media_id", mediaId);
        String requestUrl = WxPublicNo.get_material_url.replace("ACCESS_TOKEN", accessToken.getToken());
        try {
            String result = HttpClient431Util.doPost(params, requestUrl, "UTF-8");
            if(result != null) {
                JSONObject jsonMedia = JSONObject.parseObject(result);
                System.out.println(jsonMedia);
                wechatDao.saveMedia(keyName,jsonMedia);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过其media_id获取完成的素材详情，获取到图文素材详情
     */
    public JSONObject getImageText(String keyName) {
        MediaDto mediaDto = wechatDao.findMedia(keyName);
        if(mediaDto == null) {
            return null;
        }
        return mediaDto.getJsonObject();
    }



    /**
     * 发送余额模板消息
     * * @param dto  封装的模板消息
     * * @param openid  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendBalence(ModelInfoDto dto,String openId, String url) {
        Template postJSon = getTemplate(dto, openId, WxPublicNo.query_balance, url);
        sendModel(postJSon);
    }

    /**
     * 发送充值通知
     * * @param dto  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendRechargeNotice(ModelInfoDto dto,String openId, String url) {
        Template postJSon = getRechargeNoticeModel(dto, openId, WxPublicNo.recharge_notice, url);
        sendModel(postJSon);
    }

    /**
     * 封装充值通知的模板消息数据
     */
    private Template getRechargeNoticeModel(ModelInfoDto dto,String openId, String templateId, String url) {
        Template t = new Template();
        t.setTouser(openId);
        t.setTemplate_id(templateId);
        t.setUrl(url != null ? url : "javascript:void(0)");
        Map<String, TemplateData> map = new HashMap<>();
        TemplateData templateData = new TemplateData();
        if(dto.getFirst() != null) {
            templateData.setValue(dto.getFirst());
            templateData.setColor("#222222");
            map.put("first", templateData);
        }
        TemplateData templateData1 = new TemplateData();
        templateData1.setValue(dto.getKeyword1());
        templateData1.setColor("#222222");
        map.put("accountType", templateData1);
        TemplateData templateData2 = new TemplateData();
        templateData2.setValue(dto.getKeyword2());
        templateData2.setColor("#173177");
        map.put("account", templateData2);
        TemplateData templateData3 = new TemplateData();
        templateData3.setValue(dto.getKeyword3());
        templateData3.setColor("#173177");
        map.put("amount", templateData3);
        TemplateData templateData4 = new TemplateData();
        templateData4.setValue(dto.getKeyword4());
        templateData4.setColor("#173177");
        map.put("result", templateData4);
        TemplateData templateData5 = new TemplateData();
        if(dto.getRemark() != null) {
            templateData5.setValue(dto.getRemark());
            templateData5.setColor("#222222");
            map.put("remark", templateData5);
        }
        t.setData(map);
        return t;
    }

    /**
     * 发送充值成功通知
     * @param dto  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendRechargeSuccess(ModelInfoDto dto,String openId, String url) {
        Template postJSon = getRechargeSuccessModel(dto, openId, WxPublicNo.recharge_success, url);
        sendModel(postJSon);
    }

    /**
     * 封装充值成功通知模板消息数据
     */
    private Template getRechargeSuccessModel(ModelInfoDto dto,String openId, String templateId, String url) {
        Template t = new Template();
        t.setTouser(openId);
        t.setTemplate_id(templateId);
        t.setUrl(url != null ? url : "javascript:void(0)");
        Map<String, TemplateData> map = new HashMap<>();
        TemplateData templateData = new TemplateData();
        if(dto.getFirst() != null) {
            templateData.setValue(dto.getFirst());
            templateData.setColor("#222222");
            map.put("first", templateData);
        }
        TemplateData templateData1 = new TemplateData();
        templateData1.setValue(dto.getKeyword1());
        templateData1.setColor("#173177");
        map.put("money", templateData1);
        TemplateData templateData2 = new TemplateData();
        templateData2.setValue(dto.getKeyword2());
        templateData2.setColor("#173177");
        map.put("product", templateData2);
        TemplateData templateData3 = new TemplateData();
        if(dto.getRemark() != null) {
            templateData3.setValue(dto.getRemark());
            templateData3.setColor("#222222");
            map.put("remark", templateData3);
        }
        t.setData(map);
        return t;
    }


    /**
     * 发送提现申请通知
     * @param dto  封装的模板消息
     * @param url 模板链接地址
     * */
    public void sendCashApply(ModelInfoDto dto,String openId, String url) {
        Template postJSon = getTemplate(dto, openId, WxPublicNo.cash_advance_apply, url);
        sendModel(postJSon);
    }

    /**
     * 发送提现成功通知
     * @param dto  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendCashSuccess(ModelInfoDto dto,String openId, String url) {
        Template postJSon = getCashSuccessModel(dto, openId, WxPublicNo.cash_advance_success, url);
        sendModel(postJSon);
    }

    /**
     * 封装提现成功通知模板消息数据
     */
    private Template getCashSuccessModel(ModelInfoDto dto,String openId, String templateId, String url) {
        Template t = new Template();
        t.setTouser(openId);
        t.setTemplate_id(templateId);
        t.setUrl(url != null ? url : "javascript:void(0)");
        Map<String, TemplateData> map = new HashMap<>();
        TemplateData templateData = new TemplateData();
        if(dto.getFirst() != null) {
            templateData.setValue(dto.getFirst());
            templateData.setColor("#222222");
            map.put("first", templateData);
        }
        TemplateData templateData1 = new TemplateData();
        templateData1.setValue(dto.getKeyword1());
        templateData1.setColor("#173177");
        map.put("money", templateData1);
        TemplateData templateData2 = new TemplateData();
        templateData2.setValue(dto.getKeyword2());
        templateData2.setColor("#173177");
        map.put("timet", templateData2);
        TemplateData templateData3 = new TemplateData();
        if(dto.getRemark() != null) {
            templateData3.setValue(dto.getRemark());
            templateData3.setColor("#222222");
            map.put("remark", templateData3);
        }
        t.setData(map);
        return t;
    }

    /**
     * 发送提现失败通知
     * @param dto  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendCashFail(ModelInfoDto dto,String openId, String url) {
        Template postJSon = getCashFailModel(dto, openId, WxPublicNo.cash_advance_fail, url);
        sendModel(postJSon);
    }
    /**
     * 封装提现成功通知模板消息数据
     */
    private Template getCashFailModel(ModelInfoDto dto,String openId, String templateId, String url) {
        Template t = new Template();
        t.setTouser(openId);
        t.setTemplate_id(templateId);
        t.setUrl(url != null ? url : "javascript:void(0)");
        Map<String, TemplateData> map = new HashMap<>();
        TemplateData templateData = new TemplateData();
        if(dto.getFirst() != null) {
            templateData.setValue(dto.getFirst());
            templateData.setColor("#222222");
            map.put("first", templateData);
        }
        TemplateData templateData1 = new TemplateData();
        templateData1.setValue(dto.getKeyword1());
        templateData1.setColor("#173177");
        map.put("money", templateData1);
        TemplateData templateData2 = new TemplateData();
        templateData2.setValue(dto.getKeyword2());
        templateData2.setColor("#173177");
        map.put("time", templateData2);
        TemplateData templateData3 = new TemplateData();
        if(dto.getRemark() != null) {
            templateData3.setValue(dto.getRemark());
            templateData3.setColor("#222222");
            map.put("remark", templateData3);
        }
        t.setData(map);
        return t;
    }

    /**
     * 发送投资申请通知
     * @param dto  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendInvestApply(ModelInfoDto dto, String openId, String url) {
        Template postJSon = getTemplate(dto, openId, WxPublicNo.invest_apply, url);
        sendModel(postJSon);
    }

    /**
     * 发送投资状态更新
     * @param dto  封装的模板消息
     * @param url 模板链接地址
     */
    public void sendInvestStatus(ModelInfoDto dto, String openId, String url) {
        Template postJSon = getTemplate(dto, openId, WxPublicNo.invest_status, url);
        sendModel(postJSon);
    }


    /**
     * 只调用模板消息的发送模板消息接口，其他操作均可在微信管理后台中完成
     */
    private void sendModel(Template data) {
        AccessToken accessToken = getAccessToken(WxPublicNo.appId, WxPublicNo.appSecret);
        //查询数据，拼接到json中
        String requestUrl = WxPublicNo.send_psd_url.replace("ACCESS_TOKEN", accessToken.getToken());
        net.sf.json.JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "POST", net.sf.json.JSONObject.fromObject(data).toString());
        if(null != jsonObject) {
            try {
                if(jsonObject.get("errmsg").equals("ok")) {
                    logger.info("模板消息发送成功！");
                } else {
                    logger.info("模板消息发送失败！");
                }
            } catch(Exception e) {
                logger.error("模板消息发送失败, JSONObject异常！", e);
            }
        }
    }

    /**
     * 将POST请求的数据装换成json
     * @param dto  模板对象
     * @param openId  OPENID
     * @param templateId 模板ID
     * @param url 模板链接
     */
    private  Template getTemplate(ModelInfoDto dto,String openId, String templateId, String url) {
        Template t = new Template();
        Map<String, TemplateData> map = getData(dto);
        t.setTouser(openId);
        t.setTemplate_id(templateId);
        t.setUrl(url != null ? url : "javascript:void(0)");
        t.setData(map);
        return t;
    }

    private static Map<String, TemplateData> getData(ModelInfoDto dto) {
        Map<String, TemplateData> map = new HashMap<>();
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(dto);
        if(jsonObject.get("first") != null) {
            TemplateData td = new TemplateData();
            td.setValue(jsonObject.get("first").toString());
            td.setColor("#222222");
            map.put("first", td);
        }
        for(int i = 1; i <= 5; i++) {
            if(jsonObject.get("keyword"+i) != null) {
                TemplateData td = new TemplateData();
                td.setValue(jsonObject.get("keyword"+i).toString());
                td.setColor("#173177");
                map.put("keyword"+i, td);
            }
        }
        if(jsonObject.get("remark") != null) {
            TemplateData td = new TemplateData();
            td.setValue(jsonObject.get("remark").toString());
            td.setColor("#222222");
            map.put("remark", td);
        }
        return map;
    }

    /**
     * 查询微信菜单到微信菜单
     */
    public boolean getMenu(String keyWord, String key) {
        AccessToken accessToken = getAccessToken(WxPublicNo.appId, WxPublicNo.appSecret);
        net.sf.json.JSONObject jsonObject = null;
        MenuDto dto = wechatDao.findMenu();
        if(dto != null) {
            //将查询的菜单保存到缓存中，1天更新一次
            Long date = dto.getDate();
            if((new Date().getTime() - date)/1000 <= 86400) {
                jsonObject = dto.getJsonMenu();
            } else {
                String requestUrl = WxPublicNo.menu_url.replace("ACCESS_TOKEN", accessToken.getToken());
                jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
            }
        } else {
            String requestUrl = WxPublicNo.menu_url.replace("ACCESS_TOKEN", accessToken.getToken());
            jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        }

        if(jsonObject != null) {
            wechatDao.saveMenu(jsonObject);
            net.sf.json.JSONObject menu = (net.sf.json.JSONObject) jsonObject.get("menu");
            //一级菜单遍历
            if(menu.get("button") != null && net.sf.json.JSONArray.fromObject(menu.get("button")).size() > 0) {
                for (Object object : net.sf.json.JSONArray.fromObject(menu.get("button"))) {
                    net.sf.json.JSONObject jsonButton = net.sf.json.JSONObject.fromObject(object);
                    if(jsonButton.get("key") != null && jsonButton.get("key").toString().equals(key) && jsonButton.get("name").toString().equals(keyWord)) {
                        return true;
                    }
                    //二级菜单遍历
                    if(jsonButton.get("sub_button") != null && net.sf.json.JSONArray.fromObject(jsonButton.get("sub_button")).size() > 0) {
                        for (Object o : net.sf.json.JSONArray.fromObject(jsonButton.get("sub_button"))) {
                            net.sf.json.JSONObject buttonDetails = net.sf.json.JSONObject.fromObject(o);
                            if(buttonDetails.get("key") != null && buttonDetails.get("key").toString().equals(key) && buttonDetails.get("name").toString().equals(keyWord)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }


        return false;
    }


    /**
     * 获取AccessToken 凭证
     * @param appid 凭证
     * @param appsecret 秘钥
     * @return
     */
    private AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        accessToken =  wechatDao.findAceessToken();
        logger.info("redis查询结果token:"+accessToken);
        if(accessToken != null) {
            Long date = accessToken.getDate();
            //token的有效时长是：7200s, 保存到reids中的有效时长设置为5400s
            if((new Date().getTime() - date)/1000 <= 5400) {
                return accessToken;
            }
        }
        String requestUrl = WxPublicNo.access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        net.sf.json.JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                wechatDao.saveAccessToken(accessToken);
            } catch (net.sf.json.JSONException e) {
                accessToken = null;
                e.printStackTrace();
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        logger.info("微信服务器返回查询结果token:"+accessToken.getToken());

        return accessToken;
    }

}

