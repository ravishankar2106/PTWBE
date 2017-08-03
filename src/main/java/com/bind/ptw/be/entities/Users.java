package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class Users {
	private Integer userId;
	private String loginId;
	private String password;
	private String userName;
	private String teamName;
	private String emailId;
	private String phone;
	private boolean adminFlag;
	private String randomPwd;
	private UserStatus userStatus;
	private Integer userStatusId;
	private City city;
	private Integer cityId;
	private boolean pushRegistered;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "LOGIN_ID")
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Column(name = "USER_PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "TEAM_NAME")
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Column(name = "EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "ADMIN_FLAG")
	public boolean isAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(boolean adminFlag) {
		this.adminFlag = adminFlag;
	}
	
	public void setPushRegistered(boolean pushRegistered){
		this.pushRegistered = pushRegistered;
	}
	
	@Column(name = "PUSH_REGISTERED")
	public boolean getPushRegistered(){
		return this.pushRegistered;
	}
	@Column(name = "RANDOM_PWD")
	public String getRandomPwd() {
		return this.randomPwd;
	}
	public void setRandomPwd(String randomPwd) {
		this.randomPwd = randomPwd;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_STATUS_ID", referencedColumnName = "USER_STATUS_ID")
	public UserStatus getUserStatus() {
		return userStatus;
	}
	
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	@Column(name = "USER_STATUS_ID", insertable = false, updatable = false)		
	public Integer getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(Integer userStatusId) {
		this.userStatusId = userStatusId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID", referencedColumnName = "CITY_ID")
	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	@Column(name = "CITY_ID", insertable = false, updatable = false)
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	
}
