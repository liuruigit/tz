package com.jhl.controller;

import com.jhl.constant.ConfigKey;
import com.jhl.dao.NoticesDao;
import com.jhl.dto.NoticeDto;
import com.jhl.service.SysConfig;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by martin on 16/1/30.
 */
@Controller
@RequestMapping("notices")
public class NoticesController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    NoticesDao noticesDao;

    String PICUTRE_URL_COLUMN = "picture_url";//db key
    String URL_COLUMN = "URL";//db key
    String WEB_URL_COLUMN = "WEB_URL";//db key

    /**
     * 查询新闻公告接口
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public List<Map<String,Object>> queryNotices(NoticeDto dto){
        List<Map<String,Object>> notices=null;
        try {

            notices= noticesDao.queryNotices(dto.getType(),Integer.parseInt( dto.getPage()), dto.getCount());
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("invite")
    public Map<String,Object> queryInvite(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.queryInvite();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
            notices.put("invitePage",SysConfig.geteConfigByKey(ConfigKey.INVITE_REGIST_PAGE));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("award")
    public Map<String,Object> queryAward(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.queryAward();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("summary")
    public Map<String,Object> querySummary(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.querySummary();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("service")
    public Map<String,Object> queryService(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.queryService();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("pay")
    public Map<String,Object> queryPay(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.queryPay();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("withholding")
    public Map<String,Object> queryWithholding(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.queryWithholding();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    @ResponseBody
    @RequestMapping("safe")
    public Map<String,Object> querySerc(NoticeDto dto){
        Map<String,Object> notices=null;
        try {
            notices= noticesDao.querySerc();
            //添加公告相关的url
            addStaticHtmlUrl(notices);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "内容数据查询异常",e);
        }
        return notices;
    }

    /**
     * 添加notices的url
     */
    private void addStaticHtmlUrl(List<Map<String,Object>> notices){

        String image = SysConfig.geteConfigByKey(ConfigKey.STATIC_APP_DOMAIN);
        String page = SysConfig.geteConfigByKey(ConfigKey.STATIC_APP_PAGE);
        for(Map<String,Object> notice : notices ){
            notice.put(URL_COLUMN , page + notice.get(URL_COLUMN));
            notice.put(PICUTRE_URL_COLUMN , image + notice.get(PICUTRE_URL_COLUMN));
        }
    }

    private void addStaticHtmlUrl(Map<String,Object> notices){
        String image = SysConfig.geteConfigByKey(ConfigKey.STATIC_APP_DOMAIN);
        String page = SysConfig.geteConfigByKey(ConfigKey.STATIC_APP_PAGE);
        notices.put(URL_COLUMN , page + notices.get(URL_COLUMN));
        notices.put(WEB_URL_COLUMN , page + notices.get(WEB_URL_COLUMN));
        notices.put(PICUTRE_URL_COLUMN , image + notices.get(PICUTRE_URL_COLUMN));
    }
}
