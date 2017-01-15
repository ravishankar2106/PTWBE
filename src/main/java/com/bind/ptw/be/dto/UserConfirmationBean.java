package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.Date;

public class UserConfirmationBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userConfirmationId;
	private Integer userId;
	private Integer confirmationCode;
	private Integer retryCount;
	private Date codeCreationDate;
	private String email;
	
	public Integer getUserConfirmationId() {
		return userConfirmationId;
	}
	public void setUserConfirmationId(Integer userConfirmationId) {
		this.userConfirmationId = userConfirmationId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getConfirmationCode() {
		return confirmationCode;
	}
	public void setConfirmationCode(Integer confirmationCode) {
		this.confirmationCode = confirmationCode;
	}
	public Integer getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}
	public Date getCodeCreationDate() {
		return codeCreationDate;
	}
	public void setCodeCreationDate(Date codeCreationDate) {
		this.codeCreationDate = codeCreationDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
