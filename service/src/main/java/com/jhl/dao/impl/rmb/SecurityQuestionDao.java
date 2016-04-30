package com.jhl.dao.impl.rmb;

import com.jhl.dao.ISecurityQuestionDao;
import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/2.
 */
@Repository
public class SecurityQuestionDao extends BaseDao implements ISecurityQuestionDao{

    @Override
    public List<Map<String, Object>> queryQuestion(Account account) throws Exception {
        String sql = "select QUESTION from T_SECURITY_QUESTION where acc_id = ?";
        return baseDao.queryForJsonMap(sql,account.getId());
    }

    @Override
    public List<Map<String, Object>> queryQuestionAndAnswer(Account account) throws Exception {
        String sql = "select QUESTION,ANSWER from T_SECURITY_QUESTION where acc_id = ?";
        return baseDao.queryForJsonMap(sql,account.getId());
    }
}
