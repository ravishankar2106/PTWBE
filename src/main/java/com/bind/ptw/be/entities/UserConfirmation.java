package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_CONFIRMATION_PENDING")
public class UserConfirmation {
	private Integer userConfirmationPendingId;
	private Users user;
	private Integer userId;
	private Integer confirmationCode;
	private Date createdDate;
	private Integer retryCount;
	private String emailId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_CONFIRMATION_PENDING_ID", unique = true, nullable = false)
	public Integer getUserConfirmationPendingId() {
		return userConfirmationPendingId;
	}
	public void setUserConfirmationPendingId(Integer userConfirmationPendingId) {
		this.userConfirmationPendingId = userConfirmationPendingId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	@Column(name = "USER_ID", insertable = false, updatable = false)
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "CONFIRMATION_CODE")
	public Integer getConfirmationCode() {
		return confirmationCode;
	}
	public void setConfirmationCode(Integer confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "RETRY_COUNT")
	public Integer getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}
	
	@Column(name = "EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
