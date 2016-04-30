package com.jhl.cache;

import com.jhl.cache.impl.BaseCacheDaoImpl;
import com.jhl.pojo.session.TempSession;
import org.springframework.stereotype.Component;

/**
 * Created by vic wu on 2015/8/26.
 */
@Component
public class TempTokenCache extends BaseCacheDaoImpl<TempSession> {

    public static final String CHCHE_SESSION_KEY = "temp_session_list";

    public TempSession addSession(String imei) throws Exception{
        TempSession tempSession = new TempSession();
        tempSession.setImei(imei);
        tempSession.setLoginFailedTimes(0);
        this.addCache(tempSession,TempTokenCache.CHCHE_SESSION_KEY);
        return tempSession;
    }

    @Override
    public String generateDataCacheDataKey(TempSession data) {
        return data.getImei();
    }
}
