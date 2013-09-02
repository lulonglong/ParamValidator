
package org.paramvalidate.base.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * AbstractParamValidator
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public abstract class AbstractParamValidator {

	protected Map<String, String> paramsMap = new HashMap<String, String>();

	/**
	 * add param and errorCode
	 * 
	 * @param paramName
	 * @param errorCode
	 */
	public void addParam(String paramName, String errorCode) {
		paramsMap.put(paramName, errorCode);
	}

	/**
	 * use this validator validate request params
	 * 
	 * @param req
	 * @return errorCodes
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
	 * is this param error
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean isError(String content) throws Exception;
}
