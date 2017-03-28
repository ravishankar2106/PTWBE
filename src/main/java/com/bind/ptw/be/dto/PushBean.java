package com.bind.ptw.be.dto;

public class PushBean {
	
	private String contents;
	private String appIds;
	private String data;
	private String included_segments;
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAppIds() {
		return appIds;
	}
	public void setAppIds(String appIds) {
		this.appIds = appIds;
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
	

}
