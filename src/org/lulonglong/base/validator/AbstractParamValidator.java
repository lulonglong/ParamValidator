
package org.lulonglong.base.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 各个验证器的抽象基类
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public abstract class AbstractParamValidator {

	protected Map<String, String> paramsMap = new HashMap<String, String>();

	/**
	 * 添加验证参数
	 * 
	 * @param paramName
	 * @param errorCode
	 */
	public void addParam(String paramName, String errorCode) {
		paramsMap.put(paramName, errorCode);
	}

	/**
	 * 验证
	 * 
	 * @param req
	 * @return 返回错误码列表，无错误码则返回空列表
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
	 * 验证错误条件
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean isError(String content) throws Exception;
}
