package com.movit.rwe.modules.bi.sys.vo;

public class LogVo {

	private String id;

	private String title;

	private String createByName;

	private String createByCompanyName;

	private String createByOfficeName;

	private String requestUri;

	private String params;

	private String method;

	private String remoteAddr;

	private String createDate;

	private String exception;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCreateByCompanyName() {
		return createByCompanyName;
	}

	public void setCreateByCompanyName(String createByCompanyName) {
		this.createByCompanyName = createByCompanyName;
	}

	public String getCreateByOfficeName() {
		return createByOfficeName;
	}

	public void setCreateByOfficeName(String createByOfficeName) {
		this.createByOfficeName = createByOfficeName;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getCreateDate() {
		if(createDate.length()>2){
			return createDate.substring(0,createDate.length()-2);
		}
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
