
package org.paramvalidate.base.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * ������֤���ĳ������
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public abstract class AbstractParamValidator {

	protected Map<String, String> paramsMap = new HashMap<String, String>();

	/**
	 * �����֤����
	 * 
	 * @param paramName
	 * @param errorCode
	 */
	public void addParam(String paramName, String errorCode) {
		paramsMap.put(paramName, errorCode);
	}

	/**
	 * ��֤
	 * 
	 * @param req
	 * @return ���ش������б?�޴������򷵻ؿ��б�
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
	 * ��֤��������
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean isError(String content) throws Exception;
}
