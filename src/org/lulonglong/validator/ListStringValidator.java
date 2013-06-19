/**
 *  Copyright(C) 2012-2013 Suntec(Shanghai) Software Co., Ltd.
 *  All Right Reserved.
 */
package org.lulonglong.validator;

import org.lulonglong.base.validator.AbstractParamValidator;
import org.lulonglong.util.StringUtil;

/**
 * 验证是否为被指定符号分隔的列表字符串
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public class ListStringValidator extends AbstractParamValidator {

	private String split;

	public ListStringValidator( String param, String errorCode ) {
		super( param, errorCode );
	}

	/*
	 * (non-Javadoc)
	 * @see locationshare.validator.AbstractParamValidator#isError(java.lang.String)
	 */
	@Override
	protected boolean isError( String content ) throws Exception {
		return StringUtil.isNotNull( content )&&!StringUtil.isListString( content, split );
	}

	/**
	 * 设置分隔�?	 * 
	 * @param split
	 */
	public void setSplit( String split ) {
		this.split = split;
	}

}
