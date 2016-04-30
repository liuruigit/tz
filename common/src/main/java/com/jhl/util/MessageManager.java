package com.jhl.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhl.pojo.weixin.resp.Article;
import com.jhl.pojo.weixin.resp.NewsMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息处理工具
 * Created by Administrator on 2016/3/28.
 */
public class MessageManager {

    public static String getImageTextInfo(JSONObject jsonObject, Map<String, String> requestMap) {
        List<Article> articleList = new ArrayList<Article>();
        if(null != jsonObject && null != jsonObject.get("news_item")) {
            JSONArray jsonArray = jsonObject.getJSONArray("news_item");
            for(int i = 0 ; i < jsonArray.size();i++) {
                Article article = new Article();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                article.setTitle(jsonObject1.getString("title"));
                article.setUrl(jsonObject1.getString("url"));
                article.setPicUrl(jsonObject1.getString("thumb_url"));
                article.setDescription(jsonObject1.getString("digest"));
                articleList.add(article);
            }
            return getNewsMessage(requestMap, articleList);
        }
        return "";
    }

    private static String getNewsMessage(Map<String, String> requestMap, List<Article> articles) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFuncFlag(1);
        newsMessage.setArticles(articles);
        newsMessage.setArticleCount(articles.size());
        newsMessage.setToUserName(requestMap.get("FromUserName"));
        newsMessage.setFromUserName(requestMap.get("ToUserName"));
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setCreateTime(Long.parseLong(requestMap.get("CreateTime")));
        return MessageUtil.newsMessageToXml(newsMessage);
    }

}
