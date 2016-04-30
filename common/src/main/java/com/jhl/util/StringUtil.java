package com.jhl.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	/**
	 * Html转换为TextArea文本:编辑时拿来做转换
	 * @author zhengxingmiao
	 * @param str
	 * @return
	 */
	public static String Html2Text(String str) {
		if (str == null) {
			return "";
		}else if (str.length() == 0) {
			return "";
		}
		str = str.replaceAll("<br />", "\n");
		str = str.replaceAll("<br />", "\r");
		return str;
	}

	/**
	 * TextArea文本转换为Html:写入数据库时使用
	 * @author zhengxingmiao
	 * @param str
	 * @return
	 */
	public static String Text2Html(String str) {
		if (str == null) {
			return "";
		}else if (str.length() == 0) {
			return "";
		}
		str = str.replaceAll("\n", "<br />");
		str = str.replaceAll("\r", "<br />");
		return str;
	}

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}

	/**
	 * 首字符切换大小写
	 * @param str
	 * @param type
     * @return
     */
	private static String firstCaseExchage(String str,int type){
		char[] chars = new char[1];
		chars[0] = str.charAt(0);
		String temp = new String(chars);
		if(type == 0){
			return (str.replaceFirst(temp, temp.toLowerCase()));//小写
		}else if (type == 1) {
			return (str.replaceFirst(temp, temp.toUpperCase()));//大写
		}else {
			return str;
		}

	}

	public static String specialSymbolFilter(String keyword){
		if (StringUtils.isNotBlank(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

	public static String blankAndTapFilter(String cha){
		String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\n ]";
		Pattern   p   =   Pattern.compile(regEx);
		Matcher   m   =   p.matcher(cha);
		return   m.replaceAll("").trim();
	}

	public static void main(String[] args) {
		String content = blankAndTapFilter(" 张 倪安 东 ");
		System.out.println(content);
	}

	/**
	 * 首字符大写
	 * @param str
	 * @return
     */
	public static String firstToUpperCase(String str){
		return firstCaseExchage(str,1);
	}

	/**
	 * 首字符小写
	 * @param str
	 * @return
	 */
	public static String firstToLowerCase(String str){
		return firstCaseExchage(str,0);
	}
}
