package com.jhl.util;


import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.List;


/**
 * 
 * <li>类描述：数据库操作的工具类</li>
 * <li>创建者： amos.zhou</li>
 * <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-17 下午2:57:34</li>
 * <li>版本号： v1.0 </li>
 */
public final class SQLUtils {
	private SQLUtils(){}
	
	
	
	/**默认表前缀以t_开头*/
	public static String tablePrefix ="T_" ;
	/**默认的数据库主键 ID*/
	private static final String DEFAULT_PRIMARY_KEY_NAME = "ID";
	
	public static final String DEFAULT_ALL_COLUMNS = " *  ";
	
	
//	/**
//	 * 
//	 * <p>创建时间： 2013-7-16 上午10:43:01</p>
//	 * <p>获取总记录数的SQL</p>
//	 * @param sql
//	 * @return
//	 */
//	public static String getCountSQL(String sql){
//		StringBuilder buf = new StringBuilder("select count(1) as total from ( ");
//		buf.append(sql).append("  ) aa ");
//		return buf.toString();
//	}
	
	
	
	/**
	 * 
	 * <p>创建时间： 2013-7-16 上午10:49:10</p>
	 * <p>转aaBbCc转为AA_BB_CC<p>
	 * @param str
	 * @return
	 */
	public static String convertStrToDBFormat(String str){
		return str.replaceAll("[a-z](?=[A-Z]+)","$0_").toUpperCase();
	}
	
	
	/**
	 * 
	 * <p>创建时间： 2013-7-16 上午11:30:57</p>
	 * <p>获取表名</p>
	 * @param classz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getTableName(Class classz){
		return  StringUtils.trimToEmpty(tablePrefix) + convertStrToDBFormat(classz.getSimpleName());
	}

    private static boolean isNotBlank(Field field,final Object object) throws IllegalAccessException {
        field.setAccessible(true);
        Object obj = field.get(object);
        boolean result = obj!=null && StringUtils.isNotBlank(obj.toString());
        return result;
    }

    private static boolean fieldCheck(List<String> filterRange,String fieldName) {
        return filterRange.contains(fieldName);
    }

    private static void excludeField(List<String> filterRange,String fieldName) {
        filterRange.add(fieldName);
        filterRange.add(fieldName+"Begin");
        filterRange.add(fieldName+"End");
    }

}
