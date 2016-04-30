package com.jhl.dao.impl.rmb;

import com.jhl.dao.IMsgDao;
import com.jhl.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/26.
 */
@Repository(value = "iMsgDao")
public class MsgDao extends BaseDao implements IMsgDao {

    @Override
    public Map<String, Object> queryNewsMsg(Integer id) throws Exception {
        String sql = "select title from t_message where ACC_ID = ? ORDER BY CREATE_TIME limit 1";
        return baseDao.queryForMap(sql,id);
    }

    public List<Map<String, Object>> queryMessage() throws Exception {
        String sql = "select a.TITLE, a.CONTENT, a.CREATE_TIME, a.DELETE_STATE, a.ID from T_MESSAGE a where DELETE_STATE = 1 order by a.CREATE_TIME limit 0, 1";
        return baseDao.queryForJsonMap(sql);
    }

    public List<Map<String, Object>> querySysMessage() throws Exception {
        String sql = "select a.BODY, a.CREATE_TIME, a.UPDATE_TIME, a.DELETE_STATE, a.ID from T_SYSTEM_MESSAGE a where DELETE_STATE = 1 order by a.CREATE_TIME limit 0, 1";
        return baseDao.queryForJsonMap(sql);
    }

}
