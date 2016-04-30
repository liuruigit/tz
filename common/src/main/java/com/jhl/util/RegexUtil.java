/**
 * <br>Copyright  2014-1-21 第七大道-自主运营部-网站开发组
 * <br>自主运营平台WEB 上午11:59:24
 * <br>版本号： v1.0
 */

package com.jhl.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * <br>
 * 类描述：正则表达式的util <br>
 * 创建者： xin.fang <br>
 * 创建时间： 2014-1-21 上午11:59:24 <br>
 * 版本号： v1.0 <br>
 */
public class RegexUtil {


	/**
	 * 验证字符串
	 * @param regex 正则表达式
	 * @param input 待验证的字符串
	 * @return
	 */
	public static boolean regex(String regex, String input){
		if (null == regex || null == input) {
			return false;
		}
		return Pattern.matches(regex, input);
	}

	/**
	 * <br>
	 * 创建时间： 2014-1-21 下午12:02:11 <br>
	 * 创建人：xin.fang <br>
	 * 参数： <br>
	 *
	 * @param username
	 * <br>
	 * @return <br>
	 *         返回值： boolean <br>
	 *         方法描述 :验证用户名
	 */
	public static boolean regexUsername(String username) {
		if (StringUtils.isBlank(username)) {
			return false;
		}
		// 验证是否有连续4个重复的字符
		String regex = "([a-z0-9A-Z_])\\1{4,}";
		// 验证是否有6——20个字母开头，长度为20个字符
		String regex2 = "^[a-zA-Z]{1}[a-zA-Z0-9]{5,19}$";
		boolean result = Pattern.matches(regex, username) ? false : Pattern
				.matches(regex2, username);
		return result;
	}

	/**
	 * <br>
	 * 创建时间： 2014-1-21 下午12:27:00 <br>
	 * 创建人：xin.fang <br>
	 * 参数： <br>
	 * @param password <br>
	 * @return <br>
	 * 返回值： boolean <br>
	 * 方法描述 :验证密码
	 */
	public static boolean regexPassword(String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}
		String regex = "[,'\"]+";
		String regex2 = "[A-Za-z]\\S{3,15}";
		boolean result = Pattern.matches(regex, password) ? false : Pattern
				.matches(regex2, password);
		return result;
	}

	/**
	 * <br>
	 * 创建时间： 2014-1-21 下午2:40:17 <br>
	 * 创建人：xin.fang <br>
	 * 参数： <br>
	 * @param captcha <br>
	 * @return <br>
	 * 返回值： boolean <br>
	 * 方法描述 : 验证码是否符合格式
	 */
	public static boolean regexCaptcha(String captcha) {
		if (StringUtils.isBlank(captcha)) {
			return false;
		}
		String regex = "[A-Za-z0-9]{5}";
		boolean result = Pattern.matches(regex, captcha);
		return result;
	}

	/**
	 * <br>
	 * 创建时间： 2014-1-21 下午4:43:34 <br>
	 * 创建人：xin.fang <br>
	 * 参数： <br>
	 * @param content <br>
	 * @return <br>
	 * 返回值： boolean <br>
	 * 方法描述 :验证中文
	 */
	public static boolean regexZH_CN(String content) {
		if (StringUtils.isBlank(content)) {
			return false;
		}
		String regex = "[\u4e00-\u9fa5]{2,}";
		boolean result = Pattern.matches(regex, content);
		return result;
	}

	/**
	 * <br>
	 * 创建时间： 2014-1-21 下午4:51:16 <br>
	 * 创建人：xin.fang <br>
	 * 参数： <br>
	 * @param cert <br>
	 * @return <br>
	 * 返回值： boolean <br>
	 * 方法描述 : 验证身份证号
	 */
	public static boolean regexCert(String cert) {
		if (StringUtils.isBlank(cert)) {
			return false;
		}
		// 15位的身份证
		String regex1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		// 18为的身份证
		String regex2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[\\d|X]$";
		boolean result = Pattern.matches(regex1, cert) ? true : Pattern
				.matches(regex2, cert);
		return result;
	}

	/**
	 * <br>创建时间： 2014-1-22 上午11:34:14
	 * <br>创建人：xin.fang
	 * <br>参数： 
	 * <br>@param digital
	 * <br>@return
	 * <br>返回值： boolean
	 * <br>方法描述 :验证字符串是否是全数字，并且是1-9开头
	 */
	public static boolean regexDigital(String digital) {
		if (StringUtils.isBlank(digital)) {
			return false;
		}
		String regex = "[1-9]{1}\\d*";
		boolean result = Pattern.matches(regex, digital);
		return result;
	}

	public static void main(String[] args) {
//		String regex = "^http://\\w+\\S*";
//		System.out.println(Pattern.matches(regex, "http://www_"));
		//String regex = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
		String regex = "^[1-9]\\d*\\.*\\d*|0\\.\\d*[1-9]\\d*$";
		System.out.println(Pattern.matches(regex, "123.98"));
	}

    /**
     * 验证是否是日期格式(yyyy-MM-dd HH:mm:ss)
     * @param dateStr
     * @return
     */
    public static boolean regexDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return false;
        }
        String regex = "^(?:20\\d{2})\\|(?:0[1-9]|1[0-2])\\|(?:0[1-9]|[12][0-9]|3[01])\\|(?:0[0-9]|1[0-9]|2[0-3])\\|(?:[0-5]\\d)$";
        boolean result = Pattern.matches(regex, dateStr);
        return result;
    }

	/**
	 * 验证手机号码和固定电话
	 * @param dateStr
	 * @return
	 */
	public static boolean regexPhoneAndMobile(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return false;
		}
		String regex = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
		boolean result = Pattern.matches(regex, dateStr);
		return result;
	}

	public static  boolean isInteger(String number) {
		return StringUtils.isNotBlank(number) && StringUtils.isNumeric(number);
	}

	public static boolean isDouble(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
