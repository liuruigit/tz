package com.jhl.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


/**
 * 类描述：xss处理
 * 创建者： wuhaifei
 * 项目名称： platform-web
 * 创建时间： 2013-10-16 下午12:28:33
 * 版本号： v1.0
 */
public class XSSHandler {
	private static boolean escapeHTML = false;
	private static boolean escapeJavaScript = false;
	private static boolean escapeSQL = false;

	public static void setHandType(boolean escapeHTML, boolean escapeJavaScript,
								   boolean escapeSQL) {
		XSSHandler.escapeHTML = escapeHTML;
		XSSHandler.escapeJavaScript = escapeJavaScript;
		XSSHandler.escapeSQL = escapeSQL;
	}

	/**
	 * 创建时间： 2013-10-17 上午10:28:49
	 * 创建人：wuhaifei
	 * 参数： 
	 * 返回值： String
	 * 方法描述 : 根据设置的转义类型进行非法字符转义
	 */
	public static String handler(String content) {
		if (!StringUtils.isEmpty(content)) {
			if (escapeHTML)
				content = StringEscapeUtils.escapeHtml(content);
			if (escapeJavaScript)
				content = StringEscapeUtils.escapeJavaScript(content);
			if (escapeSQL)
				content = StringEscapeUtils.escapeSql(content);

		}
		return content;
	}

	/**
	 * 创建时间： 2013-10-17 上午10:28:49
	 * 创建人：wuhaifei
	 * 参数： 
	 * 返回值： String
	 * 方法描述 : 根据设置的转义类型进行非法字符转义
	 */
	public static String handle(String content, boolean escapeHTML,
								boolean escapeJavaScript, boolean escapeSQL) {
		if (!StringUtils.isEmpty(content)) {
			if (escapeSQL)
				content = StringEscapeUtils.escapeSql(content);
			if (escapeJavaScript)
				content = StringEscapeUtils.escapeJavaScript(content);
			if (escapeHTML)
				content = StringEscapeUtils.escapeHtml(content);

			// 还原中文
			content = decodeUnicode(content);
		}
		return content;
	}

	/**
	 * 创建时间： 2013-10-17 下午12:18:44
	 * 创建人：wuhaifei
	 * 参数： 
	 * 返回值： String
	 * 方法描述 : unicode转中文
	 */
	public static String decodeUnicode(String content) {
		char aChar;
		int len = content.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len; ) {
			aChar = content.charAt(x++);
			if (aChar == '\\') {
				aChar = content.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = content.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static void main(String[] args) {
//		String content = XSSHandler.handle("中\"好\"<script>alert('1');</script>", true, true, true);
//		System.out.println(content);
	}

}
