package com.jhl.cache.impl;

import com.google.common.base.Strings;
import com.jhl.cache.ICacheDao;
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
public abstract class BaseRedisCache<T> implements ICacheDao<T> {

    //JSON转换
    protected static ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(BaseRedisCache.class);

    protected abstract Jedis getJedis();

    protected abstract void release(Jedis jedis);

    @Override
    public T getData(String key,String field,Class<T> cls) {
        T data = null;
        try {
            String dataStr = getDataStr(key,field);
            if(!Strings.isNullOrEmpty(dataStr)){
                data = mapper.readValue(dataStr,cls);
            }
        } catch (IOException e) {
            logger.error(SessionUtil.getNo() + "获取区服实体失败：{}", e);
        }
        return data;
    }

    @Override
    public String getDataStr(String key,String field) {
        Jedis jedis = getJedis();
        String dataStr = jedis.hget(key,field);
        release(jedis);
        return dataStr;
    }

    @Override
    public void expire(String key, Date date) throws AppException {
        Jedis jedis = null;
        try {
            jedis = getJedis();

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
            release(jedis);
        }
    }

    @Override
    public List<String> getAllValues(String key) throws AppException {
        List<String> list = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            list = jedis.hvals(key);
        }catch (Exception e){
            throw new AppException("100","获取该key下的所有值失败!",e);
        }finally {
            release(jedis);
        }
        return list;
    }

    @Override
    public List<T> getAllValues(String key, Class<T> clazz) throws AppException {
        List<T> list = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
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
            release(jedis);
        }
        return list;
    }

    @Override
    public Set<String> getAllFields(String key) throws AppException {
        Set<String> set = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            set = jedis.hkeys(key);
        }catch (Exception e){
            throw new AppException("100","获取该key下的所有field失败!",e);
        }finally {
            release(jedis);
        }
        return set;
    }

    @Override
    public void delCacheByKeyAndField(String key, String... field) throws AppException {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            pipeline.hdel(key, field);
            pipeline.sync();
        }catch (Exception e){
            throw new AppException("100","批量删除缓存的key下的field异常!",e);
        }finally {
            release(jedis);
        }
    }

    @Override
    public void cacheData(final List<T> dataList, final String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
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
            release(jedis);
        }
    }

    @Override
    public void addCache(T data, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String cacheDataKey = generateDataCacheDataKey(data);
            String jsonDataStr = mapper.writeValueAsString(data);
            jedis.hsetnx(cacheKey, cacheDataKey, jsonDataStr);
        }catch (Exception e){
            throw new AppException("100","缓存失败!",e);
        }finally {
            release(jedis);
        }
    }

    @Override
    public void updateCache(T data, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String cacheDataKey = generateDataCacheDataKey(data);
            String jsonDataStr = mapper.writeValueAsString(data);
            jedis.hset(cacheKey, cacheDataKey, jsonDataStr);
            jedis.persist("");
        }catch (Exception e){
            throw new AppException("100","缓存更新失败!",e);
        }finally {
            release(jedis);
        }
    }

    @Override
    public void delCache(T data, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String cacheDataKey = generateDataCacheDataKey(data);
            jedis.hdel(cacheKey,cacheDataKey);
        }catch (Exception e){
            throw new AppException("100","删除失败!",e);
        }finally {
            release(jedis);
        }
    }

    @Override
    public void batchAddOrUpdateCache(List<T> dataList, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
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
            release(jedis);
        }
    }

    @Override
    public void batchDelCache(List<T> dataList, String cacheKey) throws Exception {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            for(T data : dataList) {
                String cacheDataKey = generateDataCacheDataKey(data);
                pipeline.hdel(cacheKey,cacheDataKey);
            }
            pipeline.sync();
        }catch (Exception e){
            throw new AppException("100","批量删除失败!",e);
        }finally {
            release(jedis);
        }
    }

    /**
     * 增加元素到该key下的缓存
     *
     * @param key
     * @param data
     * @throws AppException
     */
    public void sadd(String key, String... data) throws AppException {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.sadd(key, data);
        } catch (Exception e) {
            throw new AppException("100", "增加redis set类型列表异常!", e);
        } finally {
            release(jedis);
        }
    }

    /**
     * 弹出一个该key下的元素
     *
     * @param key
     * @return
     * @throws AppException
     */
    public String spop(String key) throws AppException {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.spop(key);
        } catch (Exception e) {
            throw new AppException("100", "从redis set类型中弹出元素异常!", e);
        } finally {
            release(jedis);
        }
        return result;
    }

    /**
     * 删除指定元素
     *
     * @param key
     * @param member
     * @return
     * @throws AppException
     */
    public long del(String key, String... member) throws AppException {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srem(key, member);
        } catch (Exception e) {
            throw new AppException("100", "从redis set类型中删除指定元素异常!", e);
        } finally {
            release(jedis);
        }
        return result;
    }

    /**
     * 删除该key的所有元素
     *
     * @param key
     * @throws AppException
     */
    public void remove(String key) throws AppException {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (Exception e) {
            throw new AppException("100", "从redis set类型中删除指定元素异常!", e);
        } finally {
            release(jedis);
        }
    }

    public Set<String> smembers(String key) throws AppException {
        Set<String> set = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            set = jedis.smembers(key);
        } catch (Exception e) {
            throw new AppException("100", "获取redis中set: + " + key + ", 所有元素异常!", e);
        } finally {
            release(jedis);
        }
        return set;
    }



}
