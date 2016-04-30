package com.jhl.cache;

import com.jhl.cache.impl.BaseCacheDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/2/18.
 */
@Component
public class RedisClient extends BaseCacheDaoImpl {
    @Override
    public String generateDataCacheDataKey(Object data) {
        return null;
    }
}
