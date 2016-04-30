package com.jhl.dao;

import com.jhl.pojo.biz.Account;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/2.
 */
public interface ISecurityQuestionDao {

    /**
     * 查询用户绑定的安全问题
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryQuestion(Account account) throws Exception;

    /**
     * 查询答案和问题
     * @param account
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryQuestionAndAnswer(Account account) throws Exception;

}
