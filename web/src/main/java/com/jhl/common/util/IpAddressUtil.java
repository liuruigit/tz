/**
 * Copyright  2013-3-30 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:43:31
 * 版本号： v1.0
 */

package com.jhl.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：ip地址工具类
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-30 上午10:43:31
 * 版本号： v1.0
 */
public class IpAddressUtil {


	/**
	 * 创建时间： 2013-3-30 上午10:57:06
	 * 创建人：xin.fang
	 * 参数：
	 * 返回值： String
	 * 方法描述 :获取ip地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}
}
