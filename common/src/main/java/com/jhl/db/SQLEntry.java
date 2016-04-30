package com.jhl.db;

/**
 * 
 * <li>类描述：预编译的SQL实体</li>
 * <li>创建者： amos.zhou</li>
 * <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-17 下午2:56:44</li>
 * <li>版本号： v1.0 </li>
 */
public class SQLEntry {

	/**SQL语句*/
	private String sql;
	/**SQL语句执行所需要的参数*/
	private Object[] paramValues;
	
	
	
	
	
	
	public SQLEntry() {
		super();
	}


	public SQLEntry(String sql, Object[] values) {
		super();
		this.sql = sql;
		this.paramValues = values;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public Object[] getParamValues() {
		return paramValues;
	}


	public void setParamValues(Object[] paramValues) {
		this.paramValues = paramValues;
	}
	
}
