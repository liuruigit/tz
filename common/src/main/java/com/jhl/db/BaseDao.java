package com.jhl.db;



import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User： xin.fang
 * Date： 14-6-9 
 * Time： 下午5:33
 * 基础的DAO，包含增加、删除、修改、查询等的基本操作
 */
@Repository("baseDao")
public class BaseDao {

	@Autowired(required = false)
	protected JDBCBaseDao baseDao;

	
	public <T> Integer add(T t) throws Exception {
		return baseDao.save(t);
	}

	
	public <T> int[] addBatch(List<T> list) throws Exception {
		return baseDao.saveBacth(list);
	}

	
	public <T> T queryById(Class<T> clazz, int id) throws Exception {
		return baseDao.queryById(clazz, id);
	}

	
	public <T> int update(T t) throws Exception {
		return baseDao.update(t);
	}

	public <T> Integer del(Class<T> clazz, int id) throws Exception {
		return baseDao.deleteById(clazz, id);
	}


	
	public <T> int[] delBatch(Class<T> clazz, List<Integer> IDS) throws Exception  {
		//TODO 批量删除
		return baseDao.deleteBatchByID(clazz, IDS);
	}

	
	public <T> int delByCondition(Class<T> clazz, List<SQLCondition> list) throws Exception {
		return baseDao.deleteByCondition(clazz, list);
	}

	
    public <T> int logicaDel(Class<T> clazz, String[] IDS) throws Exception{
        //TODO 批量删除
        return baseDao.logicDeleteById(clazz, IDS);
    }

	
	public <T> PaginationResult<T> query(Class<T> clazz, List<SQLCondition> list,
										 PageInfo pageInfo, String order) throws Exception {
		return baseDao.queryForPage(clazz, list, pageInfo, order);
	}

	
	public <T> List<T> queryList(Class<T> clazz, List<SQLCondition> list,
								 PageInfo pageInfo, String order) throws Exception {
		return baseDao.queryForPageOrderList(clazz, list, pageInfo, order);
	}

	
	public <T> List<T> queryList(Class<T> clazz, List<SQLCondition> list, String order) throws Exception {
		return baseDao.queryList(clazz, list, order);
	}

	public <T> List<T> queryListWithCondition(Class<T> clazz, List<SQLCondition> list, String order) throws Exception {
		return baseDao.queryListWithCondition(clazz, list, order);
	}

	
	public <T> int queryRecord(Class<T> clazz, List<SQLCondition> list) throws Exception {
		return baseDao.queryRecord(clazz, list);
	}


}
