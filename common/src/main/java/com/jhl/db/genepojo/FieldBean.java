/**
 * Copyright  2013-7-19 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:08:12
 * 版本号： v1.0
*/

package com.jhl.db.genepojo;

/**
 * <li>类描述：</li>
 * @author： amos.zhou
 *  2013-7-19 上午11:08:12
 * @since  v1.0
 */
public class FieldBean {

	private Class type;
	private String name;
	private String simpleType;
	private String remark;
	
	
	
	
	
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSimpleType() {
		return simpleType;
	}
	public void setSimpleType(String simpleType) {
		this.simpleType = simpleType;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	* @param type
	* @param name
	*/
	
	public FieldBean(Class type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	
	/**
	*/
	
	public FieldBean() {
		super();
	}
	
	
}
