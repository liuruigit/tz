package com.jhl.service;

import com.jhl.util.SpringContextHolder;
import org.springframework.util.Assert;

/**
 * Created by Hally on 2016/1/24.
 * 系统配置API
 */
public class SysConfig {

    private static ConfigService configService = SpringContextHolder.getBean("configService");

    /**
     * 获取系统配置
     * @param key
     * @return
     */
    public static String geteConfigByKey(String key) {
        Assert.notNull(configService);
        return configService.getCacheConfigByKey(key);
    }

}
