package com.bind.ptw.be.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_GROUP_USER_MAPPING")
public class UserGroupMapping {
	
	@EmbeddedId
	private UserGroupMappingKey userGroupMappingKey;

	public UserGroupMappingKey getUserGroupMappingKey() {
		return userGroupMappingKey;
	}

	public void setUserGroupMappingKey(UserGroupMappingKey userGroupMappingKey) {
		this.userGroupMappingKey = userGroupMappingKey;
	}
	
	
}
