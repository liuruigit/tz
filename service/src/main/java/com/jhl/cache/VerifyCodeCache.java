package com.jhl.cache;

import com.google.common.base.Strings;
import com.jhl.cache.impl.BaseCacheDaoImpl;
import com.jhl.pojos.VerifyCode;
import com.jhl.proxy.impl.jyt.util.StringUtil;
import com.jhl.util.SeqNoUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by vic wu on 2015/8/14.
 */
@Component
public class VerifyCodeCache extends BaseCacheDaoImpl<VerifyCode> {

    /**过期时间*/
    private static final int EXPIRE_SECOUNDS = 600;

    /**
     * 缓存验证码，10分钟有效
     * @param verifyCode
     * @throws Exception
     */
    public String cacheCode(VerifyCode verifyCode) throws Exception{
        String key = SeqNoUtil.nextSeqNo(SeqNoUtil.PREFIX_FARM_SMS);
        Assert.isTrue(!StringUtils.isEmpty(key));
        expireSet(key,verifyCode,EXPIRE_SECOUNDS);
        return key;
    }

    /**
     * 获取验证码
     * @return
     * @throws Exception
     */
    public VerifyCode getCache(String key) throws Exception{
        if (Strings.isNullOrEmpty(key))return new VerifyCode();
        return get(key,VerifyCode.class);
    }

    /**
     * 对比验证码是否正确
     * @param code
     * @return
     * @throws Exception
     */
    public boolean verifyCode(String key,String code) throws Exception{
        VerifyCode verifyCode = getCache(key);
        return verifyCode != null && StringUtil.equalsIgnoreCase(verifyCode.getCode(),code);
    }

    @Override
    public String generateDataCacheDataKey(VerifyCode data) {
        return data.getTel();
    }
}
