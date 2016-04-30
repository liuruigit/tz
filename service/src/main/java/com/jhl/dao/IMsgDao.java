package com.jhl.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/26.
 */
public interface IMsgDao {

    /**
     * 查询最新消息
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryNewsMsg(Integer id) throws Exception;

    /**
     * 查询最新的消息
     */
    public List<Map<String, Object>> queryMessage() throws Exception;

    /**
     * 查询最新的系统消息
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> querySysMessage() throws Exception;

}
