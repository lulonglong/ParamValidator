/**
 *  Copyright(C) 2012-2013 Suntec(Shanghai) Software Co., Ltd.
 *  All Right Reserved.
 */
package org.lulonglong.base.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 单项功能验证的抽象类
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public abstract class AbstractParamValidator {
	public AbstractParamValidator() {
	}

	protected Map<String, String> paramsMap = new HashMap<String, String>();

	public AbstractParamValidator(String paramName, String errorCode) {
		paramsMap.put(paramName, errorCode);
	}

	/**
	 * 向验证器添加参数
	 * 
	 * @param paramName
	 * @param errorCode
	 */
	public void addParam(String paramName, String errorCode) {
		paramsMap.put(paramName, errorCode);
	}

	/**
	 * 验证�?��加入到次验证器的参数
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public List<String> validate(HttpServletRequest req) throws Exception {
		List<String> errorCodes = new ArrayList<String>();

		for (String paramName : paramsMap.keySet()) {
			if (isError(req.getParameter(paramName))) {
				errorCodes.add(paramsMap.get(paramName));
			}
		}

		return errorCodes;
	}

	/**
	 * 验证错误的条�? *
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean isError(String content) throws Exception;
}
