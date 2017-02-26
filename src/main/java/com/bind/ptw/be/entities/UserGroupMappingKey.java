package com.bind.ptw.be.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserGroupMappingKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "USER_GROUP_ID", nullable = false)
	private Integer userGroupId;
	
	@Column(name = "USER_ID", nullable = false)
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	
	
}
