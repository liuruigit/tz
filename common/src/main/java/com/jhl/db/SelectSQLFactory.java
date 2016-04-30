/**
 * Copyright  2013-7-16 第七大道-技术支持部
 * 自主运营平台WEB 下午5:41:01
 * 版本号： v1.0
*/

package com.jhl.db;


import com.jhl.pojos.SQLCondition;
import com.jhl.pojos.PageInfo;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.jhl.db.SQLOperator.*;

/**
 * <li>类描述：查询SQL的构建工厂</li>
 * <li>创建者： amos.zhou</li>
 * <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-16 下午5:41:01</li>
 * <li>版本号： v1.0 </li>
 */
public class SelectSQLFactory extends AbstractSQLFactory {

	private SelectSQLFactory(){}

	private static volatile SelectSQLFactory instance  = new SelectSQLFactory();
	
	public static SelectSQLFactory getInstance(){
		return instance;
	}
	

	@Override
	public String buildSQLMainBody(Class entityClass,List<String> resultProperties ) {
		StringBuilder  selectSQL =  new StringBuilder(" select ").append(getQueryColumns(resultProperties)); 
		selectSQL.append(" from ").append(SQLUtils.getTableName(entityClass));
		return selectSQL.toString();
	}

    /**
     * 查询sql构建，支持模糊匹配
     * @param list
     * @return
     */
    private SQLEntry buildSQLCondition(List<SQLCondition> list) {
        StringBuilder buf = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        buf.append(" where  delete_state = 1 ");

       if (null != list) {
          for (SQLCondition condition : list) {
              if (null != condition) {
                  buf.append("  and ").append(SQLUtils.convertStrToDBFormat(condition.getKey()));
                  Object value =  condition.getValue();
                  if (condition.isLike()) {
                      buf.append(" LIKE '%" + value + "%' ");
                  } else{
                      buf.append(" = ? ");
                      params.add(value);
                  }
              }
          }
       }
        return new SQLEntry(buf.toString(),params.toArray());
    }

    /**
     * @author:vic
     * 支持多条件语句SQL拼装
     * @param list
     * @return
     */
	public SQLEntry buildSQLConditionWithOperator(List<SQLCondition> list)  {
        StringBuilder buf = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        if(list != null) {
            buf.append(" where  1 = 1 ");
            for(SQLCondition condition:list) {
                buf.append("  "+condition.getConnector() +" ").append(SQLUtils.convertStrToDBFormat(condition.getKey()));
                Object value =  condition.getValue();
                if(condition.getOperator() == null) condition.setOperator(equal);
                switch (condition.getOperator()) {
                    case equal:
                        if (condition.isLike()) {
                            buf.append(" LIKE '%" + StringEscapeUtils.escapeSql(String.valueOf(value)) + "%' ");
                        } else{
                            buf.append(" = ? ");
                            params.add(value);
                        }
                        break;
                    case in:
                        Assert.notNull(condition.getValue(),"sql注入值错误，不可为空");
                        Object[] values = (Object[])condition.getValue();
                        String subSql = inToString(values);
                        buf.append(subSql);
                        params.addAll(Arrays.asList(values));
                        break;
                    case greatEqualThan:
                        buf.append(" >= ? ");
                        params.add(value);
                        break;
                    case greatThan:
                        buf.append(" > ? ");
                        params.add(value);
                        break;
                    case lessEqualThan:
                        buf.append(" <= ? ");
                        params.add(value);
                        break;
                    case lessThan:
                        buf.append(" < ? ");
                        params.add(value);
                        break;
                    case between:
                        Assert.notNull(condition.getValue(),"sql注入值错误，不可为空!");
                        Object[] betValues = (Object[])condition.getValue();
                        buf.append(" between ? and ?");
                        params.add(betValues[0]);
                        params.add(betValues[1]);
                        break;
                    default:
                        throw new IllegalArgumentException("非法数据库逻辑运算符");
                }
            }
        }

        return new SQLEntry(buf.toString(),params.toArray());
    }
	
	/**
	 * 
	 * <p>创建时间： 2013-7-17 下午4:29:33</p>
	 * <p>完成SQL的构建</p>
	 * @param entityClass 操作类型 
	 * @param paramMap  参数列表
	 * @param resultProperties 要操作的列
	 * @return SQLEntry
	 */
	public SQLEntry getSQLEntry(Class entityClass,Map<String, Object> paramMap,List<String> resultProperties) {
		SQLEntry entry = buildSQLCondition(paramMap);
		entry.setSql(buildSQLMainBody(entityClass, resultProperties) + entry.getSql());
		return entry;
	}
	
	/**
	 * 
	 * <p>创建时间： 2013-7-16 下午5:54:35</p>
	 * <p>处理查询列</p>
	 * @param resultProperties
	 * @return
	 */
	private  String getQueryColumns(List<String> resultProperties){
		if(CollectionUtils.isEmpty(resultProperties)){
			return SQLUtils.DEFAULT_ALL_COLUMNS ;
		}
		for(int i = 0; i< resultProperties.size() ;  i++){
			resultProperties.set(i, SQLUtils.convertStrToDBFormat(resultProperties.get(i)));
		}
		String str = resultProperties.toString();
		return str.substring(1, str.length()-1);
	}


    /**
     * 获取记录数的查询语句(如果map==null则查询总的记录数, 不支持模糊查询)
     * @return
     */
    public SQLEntry getQueryRecodeEntry(Class<?> clazz, Map<String, Object> map){
        StringBuilder  selectSQL =  new StringBuilder(" select count(1) from ");
        selectSQL.append(SQLUtils.getTableName(clazz));
        selectSQL.append(" where  delete_state = 1 ");
        List<Object> paramList = new ArrayList<Object>();
        if (null != map && map.size() > 0 ) {
             Set<Map.Entry<String, Object>> entrySet = map.entrySet();
             int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                 selectSQL.append(" and ").append(SQLUtils.convertStrToDBFormat(entry.getKey())).append(" = ? ");
                paramList.add(entry.getValue());
             }
        }
        SQLEntry entry = new SQLEntry();
        entry.setSql(selectSQL.toString());
        entry.setParamValues(paramList.toArray());
        return entry;
    }


    /**
     *  获取记录数的查询语句(如果list==null或为空则查询总的记录数, 支持模糊查询)
     * @param clazz
     * @param list
     * @param <T>
     * @return
     */
    public <T> SQLEntry getQueryRecodeEntry(Class<T> clazz, List<SQLCondition> list) {
        StringBuilder  selectSQL =  new StringBuilder(" select count(1) from ");
        selectSQL.append(SQLUtils.getTableName(clazz));
        SQLEntry sqlEntry = buildSQLConditionWithOperator(list);
        selectSQL.append(sqlEntry.getSql());
        SQLEntry entry = new SQLEntry();
        entry.setSql(selectSQL.toString());
        entry.setParamValues(sqlEntry.getParamValues());
        return entry;
    }


    /**
     *
     * <p>创建时间： 2013-7-17 下午4:29:33</p>
     * <p>完成SQL的构建</p>
     * @param entityClass 操作类型
     * @param paramMap  参数列表(为null或空列表表示查询所有)
     * @return SQLEntry
     */
    public SQLEntry getPageSQLEntry(Class entityClass,Map<String, Object> paramMap, PageInfo pageInfo) {
        return this.getSqlOrderSQLEntry(entityClass, paramMap, pageInfo, null);
    }


    /**
     * 分页排序查询的 SQLEntry实体构建
     * @param entityClass 待查询的实体
     * @param paramMap 参数map
     * @param pageInfo 分页对象
     * @param order 排序语句（必须以order by开头）
     * @param <T>
     * @return
     */
    public <T> SQLEntry getSqlOrderSQLEntry(Class<T> entityClass, Map<String, Object> paramMap, PageInfo pageInfo, String order){
        Assert.notNull(entityClass, "传入的类型不能为null");
        Assert.notNull(pageInfo, "传入的分页队形不能为null");
        SQLEntry entry = buildSQLCondition(paramMap);
        StringBuilder builder = new StringBuilder();
        builder.append(buildSQLMainBody(entityClass, null)).append(entry.getSql());
        if(StringUtils.isNotBlank(order)){
            Assert.state(order.trim().toLowerCase().startsWith("order by"), "排序语句必须以order by开始");
            builder.append(" ").append(order).append(" ");
        }
        builder.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        entry.setSql(builder.toString());
        return entry;
    }

    /**
     * 分页排序查询的 SQLEntry实体构建, 支持模糊匹配
     * @param clazz 待查询的实体
     * @param list 查询条件list(SQLQueryCondition)
     * @param pageInfo 分页队形
     * @param order 啊皮鞋语句
     * @param <T>
     * @return
     */
    public <T> SQLEntry getSqlOrderSQLEntry(Class<T> clazz, List<SQLCondition> list, PageInfo pageInfo, String order) {
        Assert.notNull(clazz, "传入的类型不能为null");
        Assert.notNull(pageInfo, "传入的分页队形不能为null");
        SQLEntry entry = buildSQLConditionWithOperator(list);
        StringBuilder builder = new StringBuilder();
        builder.append(buildSQLMainBody(clazz, null)).append(entry.getSql());
        if(StringUtils.isNotBlank(order)){
            Assert.state(order.trim().toLowerCase().startsWith("order by"), "排序语句必须以order by开始");
            builder.append(" ").append(order).append(" ");
        }
        builder.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        entry.setSql(builder.toString());
        return entry;
    }

    public <T> SQLEntry getSqlOrderSQLEntry(String sql, List<SQLCondition> list, PageInfo pageInfo, String order) {
        Assert.notNull(sql, "传入的类型不能为null");
        Assert.notNull(pageInfo, "传入的分页队形不能为null");
        SQLEntry entry = buildSQLConditionWithOperator(list);
        StringBuilder builder = new StringBuilder();
        builder.append(sql).append(entry.getSql());
        if(StringUtils.isNotBlank(order)){
            Assert.state(order.trim().toLowerCase().startsWith("order by"), "排序语句必须以order by开始");
            builder.append(" ").append(order).append(" ");
        }
        builder.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        entry.setSql(builder.toString());
        return entry;
    }

    public String getSqlOrder(String sql , PageInfo pageInfo, String order) {
        Assert.notNull(sql, "传入的类型不能为null");
        Assert.notNull(pageInfo, "传入的分页队形不能为null");
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isNotBlank(order)){
            Assert.state(order.trim().toLowerCase().startsWith("order by"), "排序语句必须以order by开始");
            builder.append(" ").append(order).append(" ");
        }
        builder.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        return builder.toString();
    }

    public <T> SQLEntry getCountSQLEntry(String sql, List<SQLCondition> list) {
        Assert.notNull(sql, "传入的类型不能为null");
        SQLEntry entry = buildSQLConditionWithOperator(list);
        StringBuilder builder = new StringBuilder();
        builder.append(sql).append(entry.getSql());
        entry.setSql(builder.toString());
        return entry;
    }

	/**
	 * 排序查询的 SQLEntry实体构建, 支持模糊匹配
	 * @param clazz 待查询的实体
	 * @param list 查询条件list({@link SQLCondition})
	 * @param order 排序方式
	 * @param <T>
	 * @return
	 */
	public <T> SQLEntry getOrderSQLEntry(Class<T> clazz, List<SQLCondition> list, String order) {
		Assert.notNull(clazz, "传入的类型不能为null");
		SQLEntry entry = buildSQLCondition(list);
		StringBuilder builder = new StringBuilder();
		builder.append(buildSQLMainBody(clazz, null)).append(entry.getSql());
		if(StringUtils.isNotBlank(order)){
			Assert.state(order.trim().toLowerCase().startsWith("order by"), "排序语句必须以order by开始");
			builder.append(" ").append(order).append(" ");
		}
		entry.setSql(builder.toString());
		return entry;
	}

    public <T> SQLEntry getOrderSQLEntryWithCondition(Class<T> clazz, List<SQLCondition> list, String order) {
        Assert.notNull(clazz, "传入的类型不能为null");
        SQLEntry entry = buildSQLConditionWithOperator(list);
        StringBuilder builder = new StringBuilder();
        builder.append(buildSQLMainBody(clazz, null)).append(entry.getSql());
        if(StringUtils.isNotBlank(order)){
            Assert.state(order.trim().toLowerCase().startsWith("order by"), "排序语句必须以order by开始");
            builder.append(" ").append(order).append(" ");
        }
        entry.setSql(builder.toString());
        return entry;
    }
}
