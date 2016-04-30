/**
 * Copyright  2013-7-18 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:51:30
 * 版本号： v1.0
 */

package com.jhl.db.genepojo;

import java.sql.Types;
import java.util.Date;

/**
 * 
 * <li>类描述：完成 数据库类型至JAVA类型转换</li>
 * @author： amos.zhou
 *  2013-7-18 下午6:44:45
 * @since  v1.0
 */
public class SQLTypeProcessor {

	public Class processType(int sqlType) {
		if (checkString(sqlType)) {
			return String.class;
		}
		if (checkLong(sqlType)) {
			return Long.class;
		}
		if (checkDate(sqlType)) {
			return Date.class;
		}
		
//		if(checkTimestamp(sqlType)){
//			return Timestamp.class;
//		}

		if (checkInteger(sqlType)) {
			return Integer.class;
		}
		if (checkFloat(sqlType)) {
			return Float.class;
		}

		if (checkDouble(sqlType)) {
			return Double.class;
		}
		return Object.class;
	}

	private boolean checkDouble(int sqlType) {
		return sqlType == Types.DOUBLE;
	}

	private boolean checkFloat(int sqlType) {
		return sqlType == Types.FLOAT || sqlType == Types.NUMERIC
				|| sqlType == Types.DECIMAL;
	}


	private boolean checkInteger(int sqlType) {
		return sqlType == Types.INTEGER || sqlType == Types.SMALLINT || sqlType == Types.TINYINT;
	}

	private boolean checkDate(int sqlType) {
		return sqlType == Types.DATE 
				|| sqlType == Types.TIME || sqlType == Types.TIMESTAMP;
	}
	
	private boolean checkTimestamp(int sqlType){
		return sqlType == Types.TIMESTAMP;
	}

	private boolean checkLong(int sqlType) {
		return sqlType == Types.BIGINT;
	}

	private boolean checkString(int sqlType) {
		return sqlType == Types.VARCHAR || sqlType == Types.CHAR;
	}
}
