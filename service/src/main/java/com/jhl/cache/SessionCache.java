package com.jhl.cache;

import com.jhl.cache.impl.BaseCacheDaoImpl;
import com.jhl.pojo.biz.Account;
import org.springframework.stereotype.Component;

/**
 * Created by vic wu on 2015/8/15.
 */
@Component(value = "sessionCache")
public class SessionCache extends BaseCacheDaoImpl<Account> {

    public static final String CHCHE_SESSION_KEY = "user_session_list";

    @Override
    public String generateDataCacheDataKey(Account data) {
        return "";
    }

    /**
     * 缓存用户数据
     * @param ac
     * @throws Exception
     */
    public void cacheAccount(Account ac) throws Exception{
        System.out.println("加密数据测试---------->"+ac.getMobile());
        String key = "" + ac.getId();
        set(key,ac);
    }

    /**
     * 获取用户缓存
     * @param id
     * @return
     * @throws Exception
     */
    public Account getCache(int id) throws Exception{
        String key = "" + id;
        return get(key,Account.class);
    }
}
