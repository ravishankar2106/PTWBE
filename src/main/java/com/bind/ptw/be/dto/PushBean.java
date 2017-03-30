package com.bind.ptw.be.dto;

public class PushBean {
	
	private String contents;
	private String app_Id;
	private String data;
	private String included_segments;
	private String authParam;
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getApp_Id() {
		return app_Id;
	}
	public void setAppId(String app_Id) {
		this.app_Id = app_Id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getIncluded_segments() {
		return included_segments;
	}
	public void setIncluded_segments(String included_segments) {
		this.included_segments = included_segments;
	}
	public String getAuthParam() {
		return this.authParam;
	}
	
	public void setAuthParam(String authParam) {
		this.authParam = authParam;
	}

}
