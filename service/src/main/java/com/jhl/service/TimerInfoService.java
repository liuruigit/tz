package com.jhl.service;

import com.jhl.dao.TimerInfoDao;
import com.jhl.pojo.timer.TimerInfo;
import com.jhl.pojos.SQLCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xin.fang on 2015/9/1.
 */
@Service("timerInfoService")
public class TimerInfoService extends BaseService<TimerInfo> {

    @Resource(name = "timerInfoDao")
    private TimerInfoDao timerInfoDao;

    public TimerInfo queryByTypeAndEvnet(int type, int eventId) throws Exception {
        List<SQLCondition> sql = new ArrayList<SQLCondition>();
        sql.add(new SQLCondition("type", type));
        sql.add(new SQLCondition("eventId", eventId));
        List<TimerInfo> list = queryList(sql, "");
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<TimerInfo> getDispTimer() throws Exception {
        return timerInfoDao.getDispTimer();
    }

    public int delete(int id) throws Exception {
        return timerInfoDao.delete(id);
    }
}
