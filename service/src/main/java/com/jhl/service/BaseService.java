package com.jhl.service;


import com.google.common.base.Strings;
import com.jhl.db.BaseDao;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by xin.fang on 14-10-27.
 */
@Service
public class BaseService<T> {

    @Resource(name = "baseDao")
    protected BaseDao baseDao;

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    private int[] parsePage(String page,String count){
        Integer pageNum = 0;
        Integer countNum = 0;
        if (!Strings.isNullOrEmpty(page) || !Strings.isNullOrEmpty(count)){
            try{
                pageNum = Integer.parseInt(page);
                countNum = Integer.parseInt(count);
            }catch(Exception e){
                logger.error(SessionUtil.getNo() + "解析分页参数异常",e);
                return new int[]{0,countNum};
            }

        }else {
            logger.error(SessionUtil.getNo() + "解析分页参数异常");
            return new int[]{0,countNum};
        }
        return new int[]{(pageNum - 1) * countNum,pageNum * countNum};
    }

    protected Class<T> getEntityClass() {
        Type[] types = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments();
        Assert.notNull(types, "uninput class");
        return (Class<T>)types[0];
    }

    public Integer save(T t) throws Exception {
        Assert.notNull(t, "insert into db error");
        return baseDao.add(t);
    }

    
    public int[] addBatch(List<T> list) throws Exception {
        Assert.notNull(list, "批量增加列表不能为null");
        Assert.notEmpty(list, "批量增加列表不能为empty");
        return baseDao.addBatch(list);
    }

    
    public T queryById(int id) throws Exception {
        return baseDao.queryById(getEntityClass(), id);
    }

    
    public int update(T t) throws Exception {
        return baseDao.update(t);
    }

    
    public Integer del(int id) throws Exception {
        return baseDao.del(getEntityClass(), id);
    }

    
    public boolean delBatch(List<Integer> IDS) throws Exception {
        int [] dels= baseDao.delBatch(getEntityClass(), IDS);
        if (null != dels && dels.length > 0) {
            return true;
        }
        return false;
    }

    
    public int delByCondition(List<SQLCondition> list) throws Exception {
        return baseDao.delByCondition(getEntityClass(), list);
    }

    
    public PaginationResult<T> query(List<SQLCondition> list, PageInfo pageInfo, String order) throws Exception {
        return baseDao.query(getEntityClass(), list, pageInfo, order);
    }

    
    public List<T> queryList(List<SQLCondition> list, PageInfo pageInfo, String order) throws Exception {
        return baseDao.queryList(getEntityClass(), list, pageInfo, order);
    }

    
    public List<T> queryList(List<SQLCondition> list, String order) throws Exception {
        return baseDao.queryList(getEntityClass(), list, order);
    }

    public List<T> queryListWithCondition(List<SQLCondition> list, String order) throws Exception {
        return baseDao.queryListWithCondition(getEntityClass(), list, order);
    }
    
    public int queryRecord(List<SQLCondition> list) throws Exception {
        return baseDao.queryRecord(getEntityClass(), list);
    }


}
