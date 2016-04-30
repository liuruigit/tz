/**
 * Copyright  2013-8-21 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午2:28:16
 * 版本号： v1.0
*/

package com.jhl.db.genepojo;

/**
 * <li>类描述：数据库列</li>
 * @author： amos.zhou
 *  2013-8-21 下午2:28:16
 * @since  v1.0
 */
public class Column {

	/**列名*/
	private String columnName;
	/**列类型,参见{@link java.sql.Types} */
	private int columnType;
	/**备注*/
	private String remark;
	
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
