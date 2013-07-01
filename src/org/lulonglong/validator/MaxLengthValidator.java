/**
 *  Copyright(C) 2012-2013 Suntec(Shanghai) Software Co., Ltd.
 *  All Right Reserved.
 */
package org.lulonglong.validator;

import org.lulonglong.base.validator.AbstractParamValidator;
import org.lulonglong.util.StringUtil;

/**
 * 验证在制定编码下是否超出�?��长度
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public class MaxLengthValidator extends AbstractParamValidator {

	private int maxLength;
	private String charsetName = "utf-8";



	@Override
	protected boolean isError(String content) throws Exception {
		return StringUtil.isNotNull(content)
				&& content.getBytes(charsetName).length > maxLength;
	}

	/**
	 * 设置编码
	 * 
	 * @param charsetName
	 */
	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	/**
	 * 设置�?��长度
	 * 
	 * @param maxLength
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = Integer.parseInt(maxLength);
	}

}
