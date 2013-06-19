/**
 *  Copyright(C) 2012-2013 Suntec(Shanghai) Software Co., Ltd.
 *  All Right Reserved.
 */
package org.lulonglong.validator;

import org.lulonglong.base.validator.AbstractParamValidator;
import org.lulonglong.util.StringUtil;

/**
 * 验证是否为null或空
 * 
 * @version 2013-6-14
 * @author Suntec
 * @since JDK1.6
 * 
 */
public class NullValidator extends AbstractParamValidator {

	public NullValidator(){}
	/**
	 * @param paramName
	 * @param errorCode
	 */
	public NullValidator(String paramName, String errorCode) {
		super(paramName, errorCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * locationshare.validator.AbstractParamValidator#isError(java.lang.String)
	 */
	@Override
	protected boolean isError(String content) throws Exception {
		return StringUtil.isNullOrWhiteSpace(content);
	}

}
