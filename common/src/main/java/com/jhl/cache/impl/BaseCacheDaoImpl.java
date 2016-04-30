package com.jhl.cache.impl;

import com.google.common.base.Strings;
import com.jhl.cache.ICacheDao;
import com.jhl.cache.JedisFactory;
import com.jhl.exception.AppException;
import com.jhl.util.SessionUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vic
 * Date: 13-6-25
 * Time: 下午6:10
 * 缓存操作抽象类
 */
public abstract class BaseCacheDaoImpl<T> implements ICacheDao<T> {

    //JSON转换
    protected static ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(BaseCacheDaoImpl.class);

    @Override
    public T getData(String key,String field,Class<T> cls) {
        T data = null;
        try {
            String dataStr = getDataStr(key,field);
            if(!Strings.isNullOrEmpty(dataStr)){
                data = mapper.readValue(dataStr,cls);
            }
        } catch (IOException e) {
            logger.error(SessionUtil.getNo() + "获取区服实体失败：{}",e);
        }
        return data;
    }

    @Override
    public String getDataStr(String key,String field) {
        Jedis jedis = null;
        jedis = JedisFactory.getJedisInstance();
        String dataStr = jedis.hget(key,field);
        JedisFactory.release(jedis);
        return dataStr;
    }

    @Override
    public void expire(String key, Date date) throws AppException {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();

            if (null != date) {
                long nowTime = new Date().getTime();
                long dateTime = date.getTime();
                if (dateTime > nowTime) {
                    jedis.expire(key, (int)(dateTime - nowTime) / 1000);
                }
            }
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public List<String> getAllValues(String key) throws AppException {
        List<String> list = null;
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            list = jedis.hvals(key);
        }catch (Exception e){
            throw new AppException("100","获取该key下的所有值失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
        return list;
    }

    @Override
    public List<T> getAllValues(String key, Class<T> clazz) throws AppException {
        List<T> list = null;
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            List<String> listStr = jedis.hvals(key);
            if (null != listStr) {
                list = new ArrayList<T>();
                for (String str : listStr) {
                   list.add(mapper.readValue(str, clazz));
                }
            }
        }catch (Exception e){
            throw new AppException("100","获取该key下的所有值失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
        return list;
    }

    @Override
    public Set<String> getAllFields(String key) throws AppException {
        Set<String> set = null;
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            set = jedis.hkeys(key);
        }catch (Exception e){
            throw new AppException("100","获取该key下的所有field失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
        return set;
    }

    @Override
    public void delCacheByKeyAndField(String key, String... field) throws AppException {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            Pipeline pipeline = jedis.pipelined();
            pipeline.hdel(key, field);
            pipeline.sync();
        }catch (Exception e){
            throw new AppException("100","批量删除缓存的key下的field异常!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void cacheData(final List<T> dataList, final String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            Pipeline pipeline = jedis.pipelined();
            for(T data : dataList) {
                String cacheDataKey = generateDataCacheDataKey(data);
                String jsonDataStr = mapper.writeValueAsString(data);
                pipeline.hset(cacheKey,cacheDataKey,jsonDataStr);
            }
            pipeline.sync();
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void addCache(T data, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String cacheDataKey = generateDataCacheDataKey(data);
            String jsonDataStr = mapper.writeValueAsString(data);
            jedis.hset(cacheKey, cacheDataKey, jsonDataStr);
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void updateCache(T data, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String cacheDataKey = generateDataCacheDataKey(data);
            String jsonDataStr = mapper.writeValueAsString(data);
            jedis.hset(cacheKey, cacheDataKey, jsonDataStr);
            jedis.persist("");
        }catch (Exception e){
            throw new AppException("100","缓存更新失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void delCache(T data, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String cacheDataKey = generateDataCacheDataKey(data);
            jedis.hdel(cacheKey, cacheDataKey);
        }catch (Exception e){
            throw new AppException("100","删除失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void batchAddOrUpdateCache(List<T> dataList, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            Pipeline pipeline = jedis.pipelined();
            for(T data : dataList) {
                String cacheDataKey = generateDataCacheDataKey(data);
                String jsonDataStr = mapper.writeValueAsString(data);
                if(jedis.hexists(cacheKey,cacheDataKey)) {
                    //更新
                    pipeline.hset(cacheKey,cacheDataKey,jsonDataStr);
                }else{
                    //新增
                    pipeline.hsetnx(cacheKey, cacheDataKey, jsonDataStr);
                }
            }
            logger.info("更新缓存成功,cacheKey：{}",cacheKey);
            pipeline.sync();
        }catch (Exception e){
            throw new AppException("100","批量更新失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void batchDelCache(List<T> dataList, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            Pipeline pipeline = jedis.pipelined();
            for(T data : dataList) {
                String cacheDataKey = generateDataCacheDataKey(data);
                pipeline.hdel(cacheKey,cacheDataKey);
            }
            pipeline.sync();
        }catch (Exception e){
            throw new AppException("100","批量删除失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void delAll(String key) throws AppException {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            jedis.del(key);
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void expireSet(String key,T value,int seconds) throws Exception{
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String jsonDataStr = mapper.writeValueAsString(value);
            jedis.set(key, jsonDataStr);
            jedis.expire(key,seconds);
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void set(String key,T value) throws AppException {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String jsonDataStr = mapper.writeValueAsString(value);
            jedis.set(key, jsonDataStr);
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public T get(String key,Class<T> cls) throws AppException {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String dataStr = jedis.get(key);
            if(Strings.isNullOrEmpty(dataStr))return null;
            T data = mapper.readValue(dataStr,cls);
            return data;
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public String getStr(String key) throws AppException {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            String str = jedis.get(key);
            return str != null && str.contains("\"") ? str.replace("\"","") : str;
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

    @Override
    public void del(String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = JedisFactory.getJedisInstance();
            jedis.del(cacheKey);
        }catch (Exception e){
            throw new AppException("100","删除失败!",e);
        }finally {
            JedisFactory.release(jedis);
        }
    }

}
