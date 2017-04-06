package com.bind.ptw.be.dto;

import java.io.Serializable;

public class UserBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String userLoginId;
	private String password;
	private String userName;
	private String teamName;
	private String email;
	private String phone;
	private String token;
	private Integer userStatusId;
	private Integer cityId;
	private boolean pushRegistered;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(Integer userStatusId) {
		this.userStatusId = userStatusId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public boolean isPushRegistered() {
		return pushRegistered;
	}
	public void setPushRegistered(boolean pushRegistered) {
		this.pushRegistered = pushRegistered;
	}

}
