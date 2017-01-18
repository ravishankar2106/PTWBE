package com.bind.ptw.be.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TeamPlayerMappingKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112885868013563483L;
	
	@Column(name = "TEAM_ID", nullable = false)
	private Integer teamId;
	
	@Column(name = "PLAYER_ID", nullable = false)
	private Integer playerId;
	
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	
}
