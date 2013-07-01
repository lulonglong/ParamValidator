package org.lulonglong.vo;

import java.util.List;

/**
 * Descriptions
 * 
 * @version 2013-6-20
 * @author lulnglong
 * @since JDK1.6
 * 
 */
public class ErrorResultVO {
	private List<String> errorCodeList = null;

	/**
	 * 设置错误列表
	 * 
	 * @param errorCodes
	 */
	public void setErrorCodes(List<String> errorCodes) {
		if (errorCodes == null || errorCodes.isEmpty()) {
			return;
		}
		errorCodeList = errorCodes;
	}

	public String toXmlString() {

		StringBuilder midString = new StringBuilder();

		for (String errorCode : errorCodeList) {
			midString.append("<error code=\"" + errorCode + "\"/>");
		}

		return toXmlStringPrefix() + midString + toXmlStringSuffix();
	}

	public String toXmlStringPrefix() {
		return "<result code=\"1\">";
	}

	public String toXmlStringSuffix() {
		return "</result>";
	}
	
	/**
	 * 返回错误码列表的Json串
	 * 
	 * @return
	 */
	public  String toJsonString() {

		if (errorCodeList == null || errorCodeList.isEmpty())
			return null;

		StringBuilder midString = new StringBuilder();

		for (String errorCode : errorCodeList) {
			midString.append(",").append("\"" + errorCode + "\"");
		}

		midString.deleteCharAt(0);

		return "{\"code\":\"1\",\"errorCode\":[" + midString + "]}";
	}
}
