/**
 * Copyright  2013-8-21 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:33:24
 * 版本号： v1.0
*/

package com.jhl.db.genepojo;

/**
 * <li>类描述：表信息</li>
 * @author： amos.zhou
 *  2013-8-21 上午11:33:24
 * @since  v1.0
 */
public class Table {

	private String tableName;
	private String tableRemark;
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableRemark() {
		return tableRemark;
	}
	public void setTableRemark(String tableRemark) {
		this.tableRemark = tableRemark;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}
	
	
	
}
