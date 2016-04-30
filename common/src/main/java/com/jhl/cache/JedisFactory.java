package com.jhl.cache;

import com.google.common.base.Strings;
import com.jhl.util.DESUtil;
import com.jhl.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

@Scope
public class JedisFactory {

    private static JedisPool jedisPool;
    private static JedisPoolConfig config;
    private final static Logger log = LoggerFactory.getLogger(JedisFactory.class);
    static Properties properties = null;
    public JedisFactory() {
        super();
        init();
    }

    public static void init() {
        try {
            properties = loadConfig();
            if(properties != null){
                config = new JedisPoolConfig();
                config.setMaxTotal(Integer.parseInt(Strings.nullToEmpty(properties.getProperty("redis.pool.maxActive"))));
                config.setMaxIdle(Integer.parseInt(Strings.nullToEmpty(properties.getProperty("redis.pool.maxIdle"))));
                config.setMaxWaitMillis(Integer.parseInt(Strings.nullToEmpty(properties.getProperty("redis.pool.maxWait"))));
                config.setTestOnBorrow(Boolean.parseBoolean(Strings.nullToEmpty(properties.getProperty("redis.pool.maxWait"))));
                String jedis_ip = Strings.nullToEmpty(properties.getProperty("redis.ip"));
                log.info("redisIP:" + jedis_ip);
                String port = properties.getProperty("redis.port");
                if (StringUtils.isBlank(port) || !StringUtils.isNumeric(port)) {
                    port = "6379";
                }
                int portIn = Integer.parseInt(port);

                jedisPool = new JedisPool(config, jedis_ip, portIn, 5000);
            }else {
                log.warn("加载redis.properties失败!");
            }
        } catch (IOException e) {
            log.error("加载redis.properties出现异常",e);
        }
    }
    public static Jedis getJedisInstance() {
        try {
            if (jedisPool == null) {
                init();
            }
            Jedis jedis = jedisPool.getResource();
            jedis.auth(DESUtil.getDesString(properties.getProperty("redis.auth")));
            return jedis;
        } catch (Exception e) {
            log.error("加载异常",e);
            init();
            Jedis jedis = jedisPool.getResource();
            jedis.auth(DESUtil.getDesString(properties.getProperty("redis.auth")));
            return jedis;
        }
    }

    /**
     * 配合使用getJedisInstance方法后将jedis对象释放回连接池中
     *
     * @param jedis 使用完毕的Jedis对象
     * @return true 释放成功；否则返回false
     */
    public static boolean release(Jedis jedis) {
        if (jedisPool != null && jedis != null) {
            jedisPool.returnResource(jedis);
            return true;
        }
        return false;
    }

    public static Properties loadConfig() throws IOException {
        return PropertiesUtil.loadProperties("classpath:redis.properties");
    }

    public static void main(String[] args) {
        Jedis jedis = JedisFactory.getJedisInstance();
        System.out.println(jedis);
    }
}  