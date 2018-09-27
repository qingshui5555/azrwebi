package com.movit.rwe.modules.bi.da.test.rresult;

public class DaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误提示的多语言code
	 */
	String messageI18nCode = "";

	public DaException(String message,String messageI18nCode, Throwable cause) {
		super(message,cause);
		this.messageI18nCode = messageI18nCode;
	}

	public String getMessageI18nCode() {
		return messageI18nCode;
	}

	public void setMessageI18nCode(String messageI18nCode) {
		this.messageI18nCode = messageI18nCode;
	}
	
	
	

}
