/**
 * Copyright  2013-7-16 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午9:51:54
 * 版本号： v1.0
 */

package com.jhl.db;


import com.jhl.annotation.Transient;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.pojos.PageInfo;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <li>类描述：JDBC的常用操作的封装</li> <li>创建者： amos.zhou</li> <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-16 上午9:51:54</li> <li>版本号： v1.0</li>
 */
@Repository
public class JDBCBaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String tablePrefix;

	private InsertSQLFactory insertFactory = InsertSQLFactory.getInstance();
	private SelectSQLFactory selectFactory = SelectSQLFactory.getInstance();
	private DeleteSQLFactory deleteFactory = DeleteSQLFactory.getInstance();
	private UpdateSQLFactory updateSQLFactory = UpdateSQLFactory.getInstance();

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 上午11:36:00
	 * </p>
	 *
	 * <pre>
	 * 保存一个对象,所有不为的null或者""，以及没有标注为{@link Transient}的属性
	 * </pre>
	 *
	 * @param t
	 * @return 新插入对象的主键ID
	 */
	public <T> Integer save(T t) {
		Assert.notNull(t, "不能插入Null");
		final SQLEntry entry = insertFactory.getSQLEntry(t);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(entry.getSql(),
						new String[]{"ORDER_NO"});
				Object[] paramValue = entry.getParamValues();
				for (int i = 0; i < paramValue.length; i++) {
					ps.setObject(i + 1, paramValue[i]);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public List<Map<String, Object>> queryForMapList(String sql,Object... paras){
		return  jdbcTemplate.queryForList("select * from student", paras);
	}

	/**
	 * 插入批处理操作
	 * @param list 批处理对象集合, 不能为null或者空集合
	 * @param <T> 待批处理的对象
	 * @return 返回每条批处理数据影响的条数
	 */
	public <T> int[] saveBacth(final List<T> list) {
		Assert.notNull(list, "批处理对象集合不能为null");
		Assert.notEmpty(list, "批处理对象集合不能空集合");
		String sql = insertFactory.getSQLEntry(list.get(0)).getSql();
		BatchPreparedStatementSetter statement = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				T t = list.get(i);
				SQLEntry entry = insertFactory.getSQLEntry(t);
				Object[] paramValue = entry.getParamValues();
				for (int j = 0; j < paramValue.length; j++) {
					ps.setObject(j + 1, paramValue[j]);
				}
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		};
        int [] result = jdbcTemplate.batchUpdate(sql, statement);
        return result;
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午3:30:50
	 * </p>
	 * <p>
	 * 执行更新或者删除Sql
	 * </p>
	 *
	 * @param sql
	 *            更新的SQL
	 * @param obj
	 *            SQL参数
	 */
	public Integer update(String sql, Object... obj) {
		return jdbcTemplate.update(sql, obj);
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-17 下午5:35:04
	 * </p>
	 * <p>
	 * 执行更新或者删除Sql
	 * </p>
	 *
	 * @param sql
	 * @return
	 */
	public Integer update(String sql) {
		return jdbcTemplate.update(sql);
	}

	/**
	 *
	 * @param t
	 * @param <T>
	 * @return 返回更新影响的行数
	 */
	public <T> Integer update(T t) {
		Assert.notNull(t, "不能更新null对象");
		final SQLEntry entry = UpdateSQLFactory.getSQLEntry(t);
		return jdbcTemplate.update(entry.getSql(), entry.getParamValues());
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午6:27:59
	 * </p>
	 * <p>
	 * 删除所有
	 * </p>
	 *
	 * @param classz
	 * @return
	 */
	public <T> Integer deleteAll(Class<T> classz) {
		Assert.notNull(classz, "删除的类型不能为Null");
		return jdbcTemplate.update(deleteFactory.getSQLEntry(classz).getSql());
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午6:27:44
	 * </p>
	 * <p>
	 * 根据ID进行删除
	 * </p>
	 *
	 * @param classz
	 * @param id
	 * @return
	 */
	public <T> Integer deleteById(Class<T> classz, Integer id) {
		Assert.notNull(classz, "删除的类型不能为Null");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SQLUtils.getPrimaryKeyName(classz), id);
		return jdbcTemplate.update(deleteFactory.getSQLEntry(classz, params).getSql(), id);
	}

	/**
	 * 批量删除
	 * @param list 主键ID的集合
	 * @return
	 */
	public <T> int[] deleteBatchByID(Class<T> classz, final List<Integer> list) {
		Assert.notNull(list, "批处理对象集合不能为null");
		Assert.notEmpty(list, "批处理对象集合不能空集合");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SQLUtils.getPrimaryKeyName(classz), list.get(0));
		String sql = deleteFactory.getSQLEntry(classz, params).getSql();
		BatchPreparedStatementSetter statement = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, list.get(i));
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		};
		return jdbcTemplate.batchUpdate(sql, statement);
	}

	/**
	 * 批量删除-逻辑删除
	 * @param list 主键ID的集合
	 * @return
	 */
	public <T> int logicDeleteById(Class<T> classz, final String[] list) {
		Assert.notNull(list, "批处理对象集合不能为null");
		Assert.notEmpty(list, "批处理对象集合不能空集合");
        SQLCondition[] cols = new SQLCondition[]{new SQLCondition("deleteState", SQLOperator.equal, -1)};
        SQLCondition[] whereCons = new SQLCondition[]{new SQLCondition("id", SQLOperator.in, list)};
		String sql = updateSQLFactory.getSQLEntryByCondition(classz, cols, whereCons).getSql();
		final Object[] paras = ArrayUtils.add(list, 0, "-1");
		return jdbcTemplate.update(sql, paras);
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午6:24:51
	 * </p>
	 * <p>
	 * 根据部分属性进行删除,如果传了一个Transient字段，即会报出SQL异常
	 * </p>
	 *
	 * @param entityClass
	 * @param params
	 * @return 删除的条数
	 * @throws org.springframework.jdbc.BadSqlGrammarException
	 */
	public <T> Integer deleteByProperties(Class<T> entityClass, Map<String, Object> params) {
		Assert.notNull(entityClass, "删除的类型不能为Null");
		SQLEntry entry = deleteFactory.getSQLEntry(entityClass, params);
		return jdbcTemplate.update(entry.getSql(), entry.getParamValues());
	}

	public <T> int deleteByCondition(Class<T> clazz, List<SQLCondition> list) {
		Assert.notNull(clazz, "删除的类型不能为Null");
		Assert.notNull(list, "删除条件不能为Null");
		Assert.notEmpty(list, "删除条件不能为空");
		SQLEntry entry = deleteFactory.getSQLEntry(clazz, list);
		return jdbcTemplate.update(entry.getSql(), entry.getParamValues());
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午2:38:22
	 * </p>
	 * <p>
	 * 根据ID来查找
	 * </p>
	 *
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T queryById(Class<T> entityClass, Serializable id) {
		Assert.notNull(entityClass, "查询的类型不能为Null");
		return queryById(entityClass, id, BeanPropertyRowMapper.newInstance(entityClass));
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-23 上午10:21:56
	 * </p>
	 * <p>
	 * 根据ID来查找
	 * </p>
	 *
	 * @param entityClass
	 * @param id
	 * @param rowMapper 自定义数据库记录到实体bean的映射类，目的只为达到更高的性能要求
	 * @return
	 */
	public <T> T queryById(Class<T> entityClass, Serializable id, RowMapper<T> rowMapper) {
		Assert.notNull(entityClass, "查询的类型不能为Null");
		Assert.notNull(rowMapper, "RowMapper不能为Null");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SQLUtils.getPrimaryKeyName(entityClass), id);
		SQLEntry entry = selectFactory.getSQLEntry(entityClass, params);
		try{
		    return jdbcTemplate.queryForObject(entry.getSql(), entry.getParamValues(), rowMapper);
		} catch(Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}

	/**
	 * 根据条件查询
	 * @param entityClass
	 * @param paramsMap
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryByProperties(Class<T> entityClass, Map<String, Object> paramsMap) {
		Assert.notNull(entityClass, "查询的类型不能为Null");
		return this.queryByProperties(entityClass, paramsMap, BeanPropertyRowMapper.newInstance(entityClass));
	}

	/**
	 *
	 * <p>
	 * 创建时间：2013-7-23 上午10:21:56
	 * </p>
	 * <p>
	 * 根据属性列，来查询列表，各个条件之间关系为" and"
	 * </p>
	 *
	 * @param entityClass
	 *            操作类型
	 * @param paramsMap
	 *            参数Map
	 * @param rowMapper
	 * 			     自定义数据库记录到实体bean的映射类，目的只为达到更高的性能要求
	 * @return
	 */
	public <T> List<T> queryByProperties(Class<T> entityClass, Map<String, Object> paramsMap, RowMapper<T> rowMapper) {
		Assert.notNull(entityClass, "查询的类型不能为Null");
		SQLEntry entry = selectFactory.getSQLEntry(entityClass, paramsMap);
		return queryList(entityClass, rowMapper, entry.getSql(), entry.getParamValues());
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-17 下午12:16:38
	 * </p>
	 * <p>
	 * 查询部分属性
	 * </p>
	 *
	 * @param entityClass
	 * @param propList
	 *            要查询的属性列表
	 * @return 查询类型的结果列表
	 */
	public <T> List<T> queryPartProperties(Class<T> entityClass, List<String> propList) {
		SQLEntry entry = selectFactory.getSQLEntry(entityClass, null, propList);
		return queryList(entityClass, entry.getSql(), entry.getParamValues());
	}

	/**
	 *
	 * <p>
	 * 创建时间：2013-7-23 上午10:21:56
	 * </p>
	 * <p>
	 * 查询部分属性
	 * </p>
	 *
	 * @param entityClass
	 * @param propList
	 *            要查询的属性列表
	 * @param rowMapper
	 * 			     自定义数据库记录到实体bean的映射类，目的只为达到更高的性能要求
	 * @return 查询类型的结果列表
	 */
	public <T> List<T> queryPartProperties(Class<T> entityClass, List<String> propList, RowMapper<T> rowMapper) {
		SQLEntry entry = selectFactory.getSQLEntry(entityClass, null, propList);
		return queryList(entityClass, rowMapper, entry.getSql(), entry.getParamValues());
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午3:07:18
	 * </p>
	 * <p>
	 * 自定义查询语句
	 * </p>
	 *
	 * @param resultEntityClass
	 *            要返回的Class类型
	 * @param sql
	 *            sql语句
	 * @param args
	 *            参数值
	 * @return
	 */
	public <T> List<T> queryList(Class<T> resultEntityClass, String sql, Object... args) {
		Assert.notNull(resultEntityClass, "要返回的结果集的类型不能为Null");
		return queryList(resultEntityClass,
				BeanPropertyRowMapper.newInstance(resultEntityClass), sql, args);
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-23 上午10:21:56
	 * </p>
	 * <p>
	 * 自定义查询语句
	 * </p>
	 *
	 * @param resultEntityClass
	 *            要返回的Class类型
	 * @param sql
	 *            sql语句
	 * @param args
	 *            参数值
	 * @param rowMapper
	 * 			     自定义数据库记录到实体bean的映射类，目的只为达到更高的性能要求
	 * @return
	 */
	public <T> List<T> queryList(Class<T> resultEntityClass, RowMapper<T> rowMapper, String sql,
								 Object... args) {
		Assert.notNull(resultEntityClass, "要返回的结果集的类型不能为Null");
		Assert.notNull(rowMapper, "RowMapper不能为Null");
		return jdbcTemplate.query(sql, rowMapper, args);
	}


	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午3:17:33
	 * </p>
	 * <p>
	 * 不需要传参的自定义查询语句
	 * </p>
	 *
	 * @param resultEntityClass
	 *            要返回的Class类型
	 * @param sql
	 *            sql语句
	 * @return
	 */
	public <T> List<T> queryList(Class<T> resultEntityClass, String sql) {
		Assert.notNull(resultEntityClass, "要返回的结果集的类型不能为Null");
		return queryList(resultEntityClass, sql, new Object[0]);
	}

	/**
	 * 按条件查询对象
	 * @param clazz 待查询的对象
	 * @param list 条件list (如果为null或空则，查询全部)
	 * @param order 排序
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryList(Class<T> clazz, List<SQLCondition> list, String order) {
		SQLEntry entry = selectFactory.getOrderSQLEntry(clazz, list, order);
		List<T> resultList = jdbcTemplate.query(entry.getSql(), entry.getParamValues(), new BeanPropertyRowMapper<T>(clazz));
		return resultList;
	}

	public <T> List<T> queryListWithCondition(Class<T> clazz, List<SQLCondition> list, String order) {
		SQLEntry entry = selectFactory.getOrderSQLEntryWithCondition(clazz, list, order);
		List<T> resultList = jdbcTemplate.query(entry.getSql(), entry.getParamValues(), new BeanPropertyRowMapper<T>(clazz));
		return resultList;
	}

	/**
	 *
	 * <p>
	 * 创建时间：2013-7-23 上午10:21:56
	 * </p>
	 * <p>
	 * 不需要传参的自定义查询语句
	 * </p>
	 *
	 * @param resultEntityClass
	 *            要返回的Class类型
	 * @param sql
	 *            sql语句
	 * @param rowMapper
	 * 			     自定义数据库记录到实体bean的映射类，目的只为达到更高的性能要求
	 * @return
	 */
	public <T> List<T> queryList(Class<T> resultEntityClass, RowMapper<T> rowMapper, String sql) {
		Assert.notNull(resultEntityClass, "要返回的结果集的类型不能为Null");
		Assert.notNull(rowMapper, "RowMapper不能为Null");
		return queryList(resultEntityClass, rowMapper, sql, new Object[0]);
	}

	/**
	 * 用户查询返回单个对象的方法(如果返回多行会抛出 {org.springframework.dao.IncorrectResultSizeDataAccessExceptio})
	 * @param clazz 查询对象
	 * @param map 参数列表
	 * @param <T>
	 * @return
	 */
	public <T> T queryForObject(Class<T> clazz, Map<String, Object> map) throws Exception {
		SQLEntry entry = selectFactory.getSQLEntry(clazz, map);
		T t = null;
		try {
			t = jdbcTemplate.queryForObject(entry.getSql(), entry.getParamValues(), BeanPropertyRowMapper.newInstance(clazz));
		} catch (EmptyResultDataAccessException e) {
			//如果查询是空结果，则返回null
			t = null;
		} catch (Exception e) {
			throw e;
		}
		return t;
	}


	/**
	 * 查询返回list
	 * @param sql
	 * @param args
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T> List<T> query(String sql, Object[] args, Class<T> clazz) {
//		Assert.notNull(args, "传入的参数不能为空");
//		Assert.notNull(clazz, "传入的Class类型不能为空");
		return jdbcTemplate.query(sql, args, BeanPropertyRowMapper.newInstance(clazz));
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午3:21:27
	 * </p>
	 * <p>
	 * 查询单列
	 * </p>
	 *
	 * @param sql
	 *            查询单列SQL
	 * @return 返回查询的int值
	 * @throws org.springframework.dao.EmptyResultDataAccessException
	 *             查询不到数据时，要注意处理异常
	 */
	public int queryForInt(String sql) {
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-16 下午3:29:12
	 * </p>
	 * <p>
	 * 查询单列
	 * </p>
	 *
	 * @param sql
	 *            查询单列SQL
	 * @param obj
	 *            SQL参数
	 * @return 返回查询的int值
	 * @throws org.springframework.dao.EmptyResultDataAccessException
	 *             查询不到数据时，要注意处理异常
	 */
	public int queryForInt(String sql, Object... obj) {
		return jdbcTemplate.queryForObject(sql, Integer.class, obj);
	}

	public Double queryForDouble(String sql, Object... obj) {
		return jdbcTemplate.queryForObject(sql, Double.class, obj);
	}

	/**
	 * 查询总的记录数
	 * @return
	 */
	public int queryRecord(Class<?> clazz) {
		return this.queryRecord(clazz, new HashMap<String, Object>());
	}

	/**
	 * 查询记录数
	 * @return
	 */
	public int queryRecord(Class<?> clazz, Map<String, Object> map) {
		SQLEntry entry = selectFactory.getQueryRecodeEntry(clazz, map);
		int recode = jdbcTemplate.queryForObject(entry.getSql(), entry.getParamValues(), Integer.class);
		return recode;
	}

	/**
	 * 根据sql语句查询记录数
	 * @param sql SQL语句
	 * @param params 参数
	 * @return
	 */
	public int queryRecord(String sql, Object... params) {
		Assert.notNull(sql, "查询语句不能为空");
		int recode = jdbcTemplate.queryForObject(sql, params, Integer.class);
		return recode;
	}

	/**
	 * 根据查询条件，查询记录数, 支持模糊查询
	 * @param clazz 类型
	 * @param list 查询条件list
	 * @param <T>
	 * @return
	 */
	public <T> int queryRecord(Class<T> clazz, List<SQLCondition> list) {
		SQLEntry entry = selectFactory.getQueryRecodeEntry(clazz, list);
		int recode = jdbcTemplate.queryForObject(entry.getSql(), entry.getParamValues(), Integer.class);
		return recode;
	}

	/**
	 * 分页查询列表
	 * @param clazz
	 * @param pageInfo
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryForPageList(Class<T> clazz, PageInfo pageInfo) {
		return this.queryForPageList(clazz, null, pageInfo);
	}

	/**
	 * 按条件分页查询
	 * @param clazz
	 * @param paramMap 参数map
	 * @param pageInfo
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryForPageList(Class<T> clazz, Map<String, Object> paramMap, PageInfo pageInfo) {
		return this.queryForPageOrderList(clazz, paramMap, pageInfo, null);
	}

	/**
	 * 按条件分页查询
	 * @param clazz  类型
	 * @param paramMap 参数map
	 * @param pageInfo
	 * @param order 排序语句
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryForPageOrderList(Class<T> clazz, Map<String, Object> paramMap, PageInfo pageInfo, String order) {
		SQLEntry entry = selectFactory.getSqlOrderSQLEntry(clazz, paramMap, pageInfo, order);
		List<T> list = jdbcTemplate.query(entry.getSql(), entry.getParamValues(), new BeanPropertyRowMapper<T>(clazz));
		return list;
	}

	/**
	 * 按条件分页查询， 支持模糊匹配
	 * @param clazz 类型
	 * @param list 条件list
	 * @param pageInfo 分页
	 * @param order 排序
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryForPageOrderList(Class<T> clazz, List<SQLCondition> list, PageInfo pageInfo, String order) {
		SQLEntry entry = selectFactory.getSqlOrderSQLEntry(clazz, list, pageInfo, order);
		List<T> resultList = jdbcTemplate.query(entry.getSql(), entry.getParamValues(), new BeanPropertyRowMapper<T>(clazz));
		return resultList;
	}

	public List<Map<String, Object>> queryForPageOrderList(String sql, List<SQLCondition> list, PageInfo pageInfo, String order) {
		SQLEntry entry = selectFactory.getSqlOrderSQLEntry(sql, list, pageInfo, order);
		return jdbcTemplate.queryForList(entry.getSql(), entry.getParamValues());
	}

	/**
	 * 分页查询，已封装成PaginationResult对象
	 * @param clazz 类型
	 * @param list 条件list (如果为null或空则，查询全部)
	 * @param pageInfo 分页
	 * @param order 排序（如果为null，则按默认排序）
	 * @param <T>
	 * @return
	 */
	public <T> PaginationResult<T> queryForPage(Class<T> clazz, List<SQLCondition> list, PageInfo pageInfo, String order) {
		int total = this.queryRecord(clazz, list);
		List<T> resultList = this.queryForPageOrderList(clazz, list, pageInfo, order);
		return new PaginationResult<T>(total, resultList);
	}

	public <T> T queryForObj(Class<T> clazz,String sql,List<SQLCondition> list) throws Exception {
		SQLEntry entry = selectFactory.buildSQLConditionWithOperator(list);
		return jdbcTemplate.queryForObject(sql + entry.getSql(),clazz,entry.getParamValues());
	}


	public List<Map<String, Object>> queryForJsonMap(String sql) throws Exception {
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> queryForJsonMap(String sql,Object... paras) throws Exception {
		return jdbcTemplate.queryForList(sql, paras);
	}

	public Map<String, Object> queryForMap(String sql,Object... paras) throws Exception {
		return jdbcTemplate.queryForMap(sql, paras);
	}

	/**
	 * 分页查询
	 * @param sql
	 * @param list
	 * @param pageInfo
	 * @param order
	 * @return
	 */
	public PaginationResult<Map<String, Object>> queryForPage(String sql, List<SQLCondition> list, PageInfo pageInfo, String order) {
		String countSql = SQLUtils.buildCountSql(sql);
		SQLEntry entry = selectFactory.getCountSQLEntry(countSql, list);
		int total = jdbcTemplate.queryForObject(entry.getSql(),entry.getParamValues(),Integer.class);
		List<Map<String, Object>> resultList = this.queryForPageOrderList(sql, list, pageInfo, order);
		return new PaginationResult<Map<String, Object>>(total, resultList,pageInfo.getPageSize());
	}

	/**
	 * 分页查询
	 * @param sql
	 * @param pageInfo
	 * @param order
	 * @return
	 */
	public PaginationResult<Map<String, Object>> queryForPage(String sql,PageInfo pageInfo, String order,Object... paras) {
		String countSql = SQLUtils.buildCountSql(sql);
		int total = jdbcTemplate.queryForObject(countSql,paras,Integer.class);
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(selectFactory.getSqlOrder(sql,pageInfo,order), paras);
		return new PaginationResult<Map<String, Object>>(total, resultList,pageInfo.getPageSize());
	}
}
