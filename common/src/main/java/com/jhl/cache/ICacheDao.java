package com.jhl.cache;

import com.jhl.exception.AppException;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Version Information: Copyright© 2009 www.7road.com All rights reserved.
 * User: vic
 * Date: 13-06-25
 * Time: 上午1:37
 * Description: 缓存操作接口
 */
public interface ICacheDao<T> {

    /**
     * 缓存List对象
     * @param dataList 待缓存的数据
     * @param cacheKey redis中存储的key
     * @throws Exception
     */
    public void cacheData(List<T> dataList, String cacheKey) throws Exception;

    /**
     * 缓存对象
     * @param data
     * @param cacheKey
     * @throws Exception
     */
    public void addCache(T data, String cacheKey) throws Exception;

    /**
     * 更新对象缓存
     * @param data
     * @param cacheKey
     * @throws Exception
     */
    public void updateCache(T data, String cacheKey) throws Exception;

    /**
     * 删除对象缓存
     * @param data
     * @param cacheKey
     * @throws Exception
     */
    public void delCache(T data, String cacheKey) throws Exception;

    /**
     * 批量更新对象缓存
     * @throws Exception
     */
    public void batchAddOrUpdateCache(List<T> data, String cacheKey) throws Exception;

    /**
     * 批量删除对象缓存
     * @throws Exception
     */
    public void batchDelCache(List<T> data, String cacheKey) throws Exception;

    /**
     * 生成数据对应的key
     * @param data
     * @return
     * @throws Exception
     */
    public String generateDataCacheDataKey(T data);

    /**
     * 获取单个实体
     * @param key
     * @param field
     * @param cls
     * @return
     */
    public T getData(String key, String field, Class<T> cls);

    /**
     * 获取字符串
     * @param key
     * @return
     * @throws AppException
     */
    public String getStr(String key) throws AppException;

    /**
     * 获取单个实体字符串
     * @param key
     * @param field
     * @return
     */
    public String getDataStr(String key, String field);

    /**
     * 设置过期时间
     * @param key
     * @param date
     */
    public void expire(String key, Date date) throws AppException;

    /**
     * 获取hash中该key的所有值
     * @param key
     * @return
     * @throws AppException
     */
    public List<String> getAllValues(String key) throws AppException;

    /**
     * 获取hash中该key的所有值
     * @param key
     * @return
     * @throws AppException
     */
    public List<T> getAllValues(String key, Class<T> clazz) throws AppException;

    /**
     * 获取该key下的所有field
     * @param key
     * @return
     */
    public Set<String> getAllFields(String key) throws AppException;

    /**
     * 删除Key对应的所有数据
     * @param key
     * @throws AppException
     */
    public void delAll(String key) throws AppException;

    /**
     * 批量删除key下的field
     * @param key
     * @param field
     */
    public void delCacheByKeyAndField(String key, String... field) throws AppException;

    /**
     * 过期set缓存
     * @param key
     * @param value
     * @param seconds
     * @throws Exception
     */
    public void expireSet(String key,T value,int seconds) throws Exception;

    /**
     * set缓存
     * @param key
     * @param value
     * @throws AppException
     */
    public void set(String key,T value) throws AppException;

    /**
     * 获取值
     * @param key
     * @param cls
     * @return
     * @throws AppException
     */
    public T get(String key,Class<T> cls) throws AppException;

    /**
     * 删除缓存
     * @param cacheKey
     * @throws Exception
     */
    public void del(String cacheKey) throws Exception;
}
