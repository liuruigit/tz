package com.jhl.dao;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.jhl.cache.RedisClient;
import com.jhl.cache.SessionCache;
import com.jhl.db.BaseDao;
import com.jhl.dto.MediaDto;
import com.jhl.dto.MenuDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.weixin.function.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信数据的处理
 * Created by Administrator on 2016/3/18.
 */
@Repository(value = "wechatDao")
public class WechatDao extends BaseDao{

    @Autowired
    private RedisClient redisClient;
    @Autowired
    SessionCache sessionCache;


    /**
     * 根据openID，查询是否存在该用户的用户信息
     * @param openId
     * @return
     * @throws Exception
     */
    public Account getAccountByOpenID(String openId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openId);
        return baseDao.queryForObject(Account.class, map);
    }

    /**
     * 保存access_token
     */
    public void saveAccessToken(AccessToken accessToken) {
        accessToken.setDate(new Date().getTime());
        redisClient.set("accessToken", accessToken);
    }

    /**
     * 查询access_token
     */
    public AccessToken findAceessToken() {
        if (!Strings.isNullOrEmpty(sessionCache.getStr("accessToken"))){
            Object token = redisClient.get("accessToken", AccessToken.class);
            return (AccessToken) token;
        }
        return null;
    }

    /**
     * 保存菜单
     */
    public void saveMenu(net.sf.json.JSONObject jsonObject) {
        MenuDto dto = new MenuDto();
        dto.setDate(new Date().getTime());
        dto.setJsonMenu(jsonObject);
        redisClient.set("wxMenu", dto);
    }

    /**
     * 获取菜单
     */
    public MenuDto findMenu() {
        if (!Strings.isNullOrEmpty(sessionCache.getStr("wxMenu"))){
            return (MenuDto) redisClient.get("wxMenu", MenuDto.class);
        }
        return null;
    }

    /**
     * 将获取的素材ID保存到reids
     */
    public void saveMedia(String keyName,JSONObject jsonMedia) throws Exception {
        MediaDto dto = new MediaDto();
        dto.setJsonObject(jsonMedia);
        redisClient.set(keyName, dto);
    }

    /**
     * 获取图文素材
     */
    public MediaDto findMedia(String keyName) {
        if (!Strings.isNullOrEmpty(sessionCache.getStr(keyName))){
            return (MediaDto) redisClient.get(keyName, MediaDto.class);
        }
        return null;
    }

}
