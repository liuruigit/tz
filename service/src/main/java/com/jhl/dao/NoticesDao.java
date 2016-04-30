package com.jhl.dao;

import com.jhl.db.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 网站公告资讯
 * Created by martin on 16/1/30.
 */
@Repository
public class NoticesDao extends BaseDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> queryNotices(String contentType , int page , int count ){
        String sql = "select t.title,t.URL,t.WEB_URL,t.summary,t.notices_id,t.picture_url,t.create_time,t.index,t.content_type from t_notices t  where t.content_type = ? and DELETE_STATE = 1 order by t.index desc ,t.create_time desc limit ? , ? ";
        List<Map<String,Object>> notices = jdbcTemplate.queryForList(sql, new Object[]{contentType, page , count});
        return notices;
    }

    public Map<String,Object> queryNotices(Object[] objects){
        String sql = "select t.title,t.URL,t.WEB_URL,t.summary,t.notices_id,t.picture_url," +
                "t.create_time,t.index,t.content_type from t_notices t  where t.content_type = ?" +
                " and DELETE_STATE = 1 order by t.index desc ,t.create_time desc limit 1 ";
        Map<String,Object> notices = jdbcTemplate.queryForMap(sql, objects);
        return notices;
    }

    public Map<String,Object> queryContract(Object[] objects){
        String sql = "select CAST(t.`CONTENT` AS CHAR CHARACTER SET utf8) AS `CONTENT` from t_notices t  where t.content_type = ? and t.title = ?" +
                " and DELETE_STATE = 1 ";
        Map<String,Object> notices = jdbcTemplate.queryForMap(sql, objects);
        return notices;
    }

    /**
     * 邀请奖励
     * @return
     */
    public Map<String, Object> queryInvite() {
        return queryNotices(new Object[]{"INVITE"});
    }

    /**
     * 奖励规则
     * @return
     */
    public Map<String, Object> queryAward() {
        return queryNotices(new Object[]{"AWARD"});
    }

    /**
     * 公司简介
     * @return
     */
    public Map<String, Object> querySummary() {
        return queryNotices(new Object[]{"SUMMARY"});
    }

    /**
     * 金葫芦服务协议
     * @return
     */
    public Map<String, Object> queryService() {
        return queryNotices(new Object[]{"SERVICE"});
    }

    /**
     * 金葫芦支付协议
     * @return
     */
    public Map<String, Object> queryPay() {
        return queryNotices(new Object[]{"PAY"});
    }

    /**
     * 金葫芦代扣委托协议
     * @return
     */
    public Map<String, Object> queryWithholding() {
        return queryNotices(new Object[]{"WITHHOLDING"});
    }

    /**
     * 多重保障
     * @return
     */
    public Map<String, Object> querySerc() {
        return queryNotices(new Object[]{"SAFEGUARD"});
    }


}
