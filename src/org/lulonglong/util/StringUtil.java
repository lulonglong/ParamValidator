/**
 *  Copyright(C) 2012 Suntec Software(Shanghai) Co., Ltd.
 *  All Right Reserved.
 */
package org.lulonglong.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Descriptions
 * 
 * @version 2012-11-28
 * @author Suntec
 * @since JDK1.6
 * 
 */
public class StringUtil {

	public static final String Empty = "";

	public static boolean isNotNull(String str) {

		boolean result = false;
		if (str != null && str.trim().length() > 0) {
			result = true;
		}

		return result;
	}

	/**
	 * 判断是否手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		String pattern = "\\d+";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否邮件地址 合法E-mail地址�?1. 必须包含�?��并且只有�?��符号“@�?2.
	 * 第一个字符不得是“@”或者�?.�?3. 不允许出现�?@.”或�?@ 4. 结尾不得是字符�?@”或者�?.�?5.
	 * 允许“@”前的字符中出现“＋�?6. 不允许�?＋�?在最前面，或者�?＋@�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmailAddress(String str) {
		String pattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();

	}

	/**
	 * 判断是否为整�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPositiveInteger(String str) {
		if (!isNotNull(str))
			return false;

		return Pattern.matches("\\d+", str.trim());
	}

	/**
	 * 判断是否为整�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (!isNotNull(str))
			return false;

		return Pattern.matches("-?\\d+", str.trim());
	}

	/**
	 * 判断是否为小�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPositiveDecimal(String str) {
		if (!isNotNull(str))
			return false;

		return Pattern.matches("\\d+\\.\\d+", str.trim());
	}

	/**
	 * 判断是否为小�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		if (!isNotNull(str))
			return false;

		return Pattern.matches("-?\\d+\\.\\d+", str.trim());
	}

	/**
	 * 是否为正�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPositiveNumber(String str) {
		return isPositiveDecimal(str) || isPositiveInteger(str);
	}

	/**
	 * 是否为数�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return isDecimal(str) || isInteger(str);
	}

	/**
	 * 判断是否为空�? *
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrWhiteSpace(String str) {
		return !isNotNull(str);
	}

	/**
	 * 判断是否被split分割的列表字符串 如：1s,we,ff,ff
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static boolean isListString(String str, String split) {

		if (isNullOrWhiteSpace(str) || str.endsWith(split)
				|| str.startsWith(split))
			return false;

		for (String item : str.split(split)) {
			if (isNullOrWhiteSpace(item))
				return false;
		}

		return true;
	}

	public static String getSetMethodString(String methodName) {
		if (isNullOrWhiteSpace(methodName))
			return null;
		return "set"+methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
	}
}
