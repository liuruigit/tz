/**
 * Copyright  2013-7-16 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:30:06
 * 版本号： v1.0
 */

package com.jhl.db;



import java.util.*;

/**
 * <li>类描述：可以带where条件的SQL语句的抽象的工厂</li> <li>创建者： amos.zhou</li> <li>项目名称： 7road-common</li> <li>创建时间：
 * 2013-7-16 下午4:30:06</li> <li>版本号： v1.0</li>
 */
public abstract class AbstractSQLFactory {




	/**
	 * 
	 * <p>
	 * 创建时间： 2013-7-16 下午5:12:21
	 * </p>
	 * <p>
	 * 判断是否需要where语句块
	 * </p>
	 * 
	 * @return
	 */
	protected boolean isWhereClaudNeed(Map<String, Object> paramMap) {
		return paramMap != null && paramMap.size() > 0;
	}

	

	
	
	
	/**
	 * 
	 * <p>创建时间： 2013-7-17 下午4:29:33</p>
	 * <p>完成SQL的构建</p>
	 * @param entityClass 操作类型 
	 * @param paramMap  参数列表
	 * @return
	 */
	public SQLEntry getSQLEntry(Class entityClass,Map<String, Object> paramMap) {
		SQLEntry entry = buildSQLCondition(paramMap);
		entry.setSql(buildSQLMainBody(entityClass, null) + entry.getSql());
		return entry;
	}

	/**
	 * 
	 * <p>创建时间： 2013-7-17 下午4:50:53</p>
	 * <p>完成SQL的构建,无参重载版</p>
	 * @param entityClass
	 * @return
	 */
	public SQLEntry getSQLEntry(Class entityClass) {
		return getSQLEntry(entityClass,null);
	}

	/**
	 * 
	 * <p>
	 * 创建时间： 2013-7-16 下午5:37:33
	 * </p>
	 * <p>
	 * 构建SQL语句的where部分
	 * </p>
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected SQLEntry buildSQLCondition(Map<String, Object> paramMap) {
		if (!isWhereClaudNeed(paramMap)) {
			return new SQLEntry("",new Object[0]);
		}
		StringBuilder buf = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		
		buf.append(" where 1=1  ");

		Set<String> set = paramMap.keySet();
		Iterator<String> it = set.iterator();

		while (it.hasNext()) {
			buf.append("  and ");
			String fieldName = it.next();
			buf.append(SQLUtils.convertStrToDBFormat(fieldName));
			buf.append(" = ? ");
			params.add(paramMap.get(fieldName));
		}
		return new SQLEntry(buf.toString(),params.toArray());
	}



	/**
	 * 
	 * <p>
	 * 创建时间： 2013-7-16 下午5:38:23
	 * </p>
	 * <p>
	 * 构建sql语句的主体部分
	 * </p>
	 * 
	 * @return
	 */
	protected abstract  String buildSQLMainBody(Class entityClass,List<String> resultProperties);

}
