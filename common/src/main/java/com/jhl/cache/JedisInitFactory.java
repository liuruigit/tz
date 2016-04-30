package com.jhl.cache;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 为了支持
 */
@Scope
public class JedisInitFactory {

    private JedisPool jedisPool;
    private JedisPoolConfig config;
    private Logger log = LoggerFactory.getLogger(JedisInitFactory.class);

    public JedisInitFactory() {
        init();
    }

    public JedisInitFactory(String ip, int port) {
        init(ip, port);
    }


    private void initConifg() {
        config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(Strings.nullToEmpty(RedisConfig.getConfig("redis.pool.maxActive"))));
        config.setMaxIdle(Integer.parseInt(Strings.nullToEmpty(RedisConfig.getConfig("redis.pool.maxIdle"))));
        config.setMaxWaitMillis(Integer.parseInt(Strings.nullToEmpty(RedisConfig.getConfig("redis.pool.maxWait"))));
        config.setTestOnBorrow(Boolean.parseBoolean(Strings.nullToEmpty(RedisConfig.getConfig("redis.pool.maxWait"))));
    }

    private void init() {
        try {
            initConifg();
            String jedis_ip = Strings.nullToEmpty(RedisConfig.getConfig("redis.ip"));
            log.info("redisIP:" + jedis_ip);
            String port = RedisConfig.getConfig("redis.port");
            if (StringUtils.isBlank(port) || !StringUtils.isNumeric(port)) {
                port = "6379";
            }
            int portIn = Integer.parseInt(port);

            jedisPool = new JedisPool(config, jedis_ip, portIn, 5000);
        } catch (Exception e) {
            log.error("加载redis.properties出现异常!",e);
        }
    }

    private void init(String ip, int port) {
        try {
            initConifg();
            jedisPool = new JedisPool(config, ip, port, 5000);
        } catch (Exception e) {
            log.error("加载redis.properties出现异常!",e);
        }
    }

    public Jedis getJedisInstance() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            init();
            return jedisPool.getResource();
        }
    }

    /**
     * 配合使用getJedisInstance方法后将jedis对象释放回连接池中
     *
     * @param jedis 使用完毕的Jedis对象
     * @return true 释放成功；否则返回false
     */
    public boolean release(Jedis jedis) {
        if (jedisPool != null && jedis != null) {
            jedisPool.returnResource(jedis);
            return true;
        }
        return false;
    }

}