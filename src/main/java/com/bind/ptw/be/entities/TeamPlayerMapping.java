package com.bind.ptw.be.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM_PLAYER_MAPPING")
public class TeamPlayerMapping {
	
	@EmbeddedId
	private TeamPlayerMappingKey teamPlayerMappingKey;

	public TeamPlayerMappingKey getTeamPlayerMappingKey() {
		return teamPlayerMappingKey;
	}

	public void setTeamPlayerMappingKey(TeamPlayerMappingKey teamPlayerMappingKey) {
		this.teamPlayerMappingKey = teamPlayerMappingKey;
	}
	
	
	
	
}
