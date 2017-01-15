package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_GROUP_USER_MAPPING")
public class UserGroupMapping {
	private Integer userMappingId;
	private Integer userGroupId;
	private Integer userId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_GROUP_USER_MAPPING_ID", unique = true, nullable = false)
	public Integer getUserMappingId() {
		return userMappingId;
	}
	public void setUserMappingId(Integer userMappingId) {
		this.userMappingId = userMappingId;
	}
	
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "USER_GROUP_ID")
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	
	
}
