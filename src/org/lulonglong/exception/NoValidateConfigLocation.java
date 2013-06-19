package org.lulonglong.exception;

public class NoValidateConfigLocation extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6622123459621877068L;

	public NoValidateConfigLocation(String arg0) {
		super("没有找到validateConfigLocation配置项"+arg0);
		// TODO Auto-generated constructor stub
	}

}
