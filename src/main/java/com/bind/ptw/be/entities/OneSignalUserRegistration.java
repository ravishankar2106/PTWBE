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
@Table(name = "ONE_SIGNAL_USER_REGISTRATION")
public class OneSignalUserRegistration {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ONE_SIGNAL_USER_REGISTRATION_ID", unique = true, nullable = false)
	private Integer oneSignalUserRegistraionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private Users user;
	
	@Column(name = "CONTEST_QUESTION_ID", insertable = false, updatable = false)
	private Integer userId;
	
	@Column(name = "ONE_SIGNAL_REGISTRATION_ID", nullable = true)
	private String oneSignalRegistrationId;
		
	
	public Integer getOneSignalUserRegistraionId() {
		return oneSignalUserRegistraionId;
	}
	public void setOneSignalUserRegistraionId(Integer oneSignalUserRegistraionId) {
		this.oneSignalUserRegistraionId = oneSignalUserRegistraionId;
	}
	
	public Users getUsers() {
		return user;
	}
	
	public void setUsers(Users user) {
		this.user = user;
	}
	
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
