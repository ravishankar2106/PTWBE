package com.bind.ptw.be.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_GROUP_USER_MAPPING")
public class UserGroupMapping {
	
	@EmbeddedId
	private UserGroupMappingKey userGroupMappingKey;
	
	@Column(name = "TOURNAMENT_ID")
	private Integer tournamentId;

	public UserGroupMappingKey getUserGroupMappingKey() {
		return userGroupMappingKey;
	}

	public void setUserGroupMappingKey(UserGroupMappingKey userGroupMappingKey) {
		this.userGroupMappingKey = userGroupMappingKey;
	}

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	
}
