/**
 * Copyright  2013-7-19 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:39:25
 * 版本号： v1.0
*/

package com.jhl.db.genepojo;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

import java.util.*;

/**
 * <li>类描述：</li>
 * @author： amos.zhou
 *  2013-7-19 上午10:39:25
 * @since  v1.0
 */
public class FieldHolder {
	
	private static final String  JAVA_LANG = "java.lang";

	private List<FieldBean> list = new ArrayList<FieldBean>();
	private SQLTypeProcessor processor = new SQLTypeProcessor();
	private Set<String> importSet = new  HashSet<String>();

	
	
	
	public List<FieldBean> getFieldList() {
		return list;
	}
	
	public Set<String> getImportType(){
		Iterator<String> it = importSet.iterator();
		while(it.hasNext()){
			if(StringUtils.containsIgnoreCase(it.next(), JAVA_LANG)){
				it.remove();
			}
		}
		return importSet;
	}




	public FieldHolder(List<Column> columnList){
		Assert.notEmpty(columnList,"不能实例化FiledHolder,传入列名列表为空");
		for(Iterator<Column> it = columnList.iterator() ; it.hasNext() ;){
			while(it.hasNext()){
				Column cl = it.next();
				columnToBeanInfo(cl);
			}
		}
	}

	
	private void columnToBeanInfo(Column cl ) {
		Class  type = processor.processType(cl.getColumnType());
		FieldBean fb = new FieldBean();
		fb.setType(type);
		fb.setName(JdbcUtils.convertUnderscoreNameToPropertyName(cl.getColumnName()));
		fb.setSimpleType(type.getSimpleName());
		fb.setRemark(cl.getRemark());
		importSet.add(type.getName());
		list.add(fb);
	}
	
}
