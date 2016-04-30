package com.jhl.dao;

import com.jhl.db.BaseDao;
import com.jhl.pojo.timer.TimerInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by xin.fang on 2015/9/1.
 */
@Repository("timerInfoDao")
public class TimerInfoDao extends BaseDao {

    private static final String QUERY_DISP_TIMER = "SELECT * FROM t_timer_info WHERE time <= ? ";

    private static final String DELETE_SQL = "DELETE from t_timer_info where id = ? ";

    public List<TimerInfo> getDispTimer() throws Exception {
        return baseDao.query(QUERY_DISP_TIMER, new Object[]{new Date()}, TimerInfo.class);
    }

    public int delete(int id) throws Exception{
        return baseDao.update(DELETE_SQL, new Object[]{id});
    }
}
