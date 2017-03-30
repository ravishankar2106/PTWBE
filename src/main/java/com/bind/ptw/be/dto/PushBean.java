package com.bind.ptw.be.dto;

import java.util.Map;

public class PushBean {
	
	private Map<String, String> contents;
	private String app_id;
	private Map<String, String> data;
	private String included_segments;
	private String authParam;
	private String[] include_player_ids;
	
	public Map<String, String> getContents() {
		return contents;
	}
	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
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
	public String[] getInclude_player_ids() {
		return include_player_ids;
	}
	public void setInclude_player_ids(String[] include_player_ids) {
		this.include_player_ids = include_player_ids;
	}

}
