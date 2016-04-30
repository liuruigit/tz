/**
 * Copyright  2013-7-16 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:46:16
 * 版本号： v1.0
*/

package com.jhl.db;


import com.jhl.pojos.SQLCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * <li>类描述：删除语句工厂</li>
 * <li>创建者： amos.zhou</li>
 * <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-16 下午4:46:16</li>
 * <li>版本号： v1.0 </li>
 */
public class DeleteSQLFactory extends AbstractSQLFactory {
	
	private DeleteSQLFactory(){}

	private static volatile DeleteSQLFactory instance  = new DeleteSQLFactory();
	
	public static DeleteSQLFactory getInstance(){
		return instance;
	}
	


	/* (non-Javadoc)
	* @see com.sz7road.common.sql.factory.support.AbstractSQLFactory#buildSQLMainBody(java.lang.Class, java.util.List)
	*/
	
	@Override
	protected String buildSQLMainBody(Class classz,
			List<String> resultProperties) {
		//StringBuilder  deleteSQL =  new StringBuilder("update from ").append(SQLUtils.getTableName(entityClass));
		StringBuilder buf = new StringBuilder(" UPDATE ").append(SQLUtils.getTableName(classz)).append(" SET delete_state = -1 ");
		return buf.toString();
	}


	public <T> SQLEntry getSQLEntry(Class<T> clazz, List<SQLCondition> list) {
		SQLEntry entry = buildSQLCondition(list);
		entry.setSql(buildSQLMainBody(clazz, null) + entry.getSql());
		return entry;
	}

	private SQLEntry buildSQLCondition(List<SQLCondition> list) {
		if (null == list || list.size() == 0) {
			return new SQLEntry("",new Object[0]);
		}
		StringBuilder buf = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		buf.append(" where 1=1 ");

		for (SQLCondition condition : list) {
			buf.append("  and ");
			String fieldName = condition.getKey();
			buf.append(SQLUtils.convertStrToDBFormat(fieldName));
			buf.append(" = ? ");
			params.add(condition.getValue());
		}
		return new SQLEntry(buf.toString(),params.toArray());
	}
}
