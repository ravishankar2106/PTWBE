package com.bind.ptw.be.dto;

import java.io.Serializable;

public class OneSignalUserRegistrationBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String oneSignalRegistrationId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOneSignalRegistrationId() {
		return oneSignalRegistrationId;
	}
	public void setOneSignalRegistrationId(String oneSignalRegistrationId) {
		this.oneSignalRegistrationId = oneSignalRegistrationId;
	}
	
	
}
