package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_STATUS")
public class UserStatus {
	private Integer userStatusId;
	private String userStatusName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_STATUS_ID", unique = true, nullable = false)
	public Integer getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(Integer userStatusId) {
		this.userStatusId = userStatusId;
	}
	
	@Column(name = "USER_STATUS_NAME")
	public String getUserStatusName() {
		return userStatusName;
	}
	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}
	
}
