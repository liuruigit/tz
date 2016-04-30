package com.jhl.dao.impl.rmb;

import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.InviteRewardConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/3/26.
 */
@Repository("inviteConfigDao")
public class InviteConfigDao extends BaseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public InviteRewardConfig queryConfig(double investMoney) throws Exception {
        InviteRewardConfig config = null;
        try {
            String sql = "SELECT c.LIMIT,c.ID,c.DAYS,c.RANGE_START,c.RANGE_END,c.PERC from t_invite_reward_config c " +
                    "where c.RANGE_START < ? and c.RANGE_END >= ? and DELETE_STATE = 1";
            config = jdbcTemplate.queryForObject(sql, new Object[]{investMoney,investMoney},
                    BeanPropertyRowMapper.newInstance(InviteRewardConfig.class));
        } catch (EmptyResultDataAccessException exceptione) {
            //查询的用户不存在
        }
        return config;
    }

}
