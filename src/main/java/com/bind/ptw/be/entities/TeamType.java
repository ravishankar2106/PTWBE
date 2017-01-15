package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEAM_TYPE")
public class TeamType {
	private Integer teamTypeId;
	private String teamTypeName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TEAM_TYPE_ID", unique = true, nullable = false)
	public Integer getTeamTypeId() {
		return teamTypeId;
	}
	public void setTeamTypeId(Integer teamTypeId) {
		this.teamTypeId = teamTypeId;
	}
	
	@Column(name = "TEAM_TYPE_NAME")
	public String getTeamTypeName() {
		return teamTypeName;
	}
	public void setTeamTypeName(String teamTypeName) {
		this.teamTypeName = teamTypeName;
	}
	
}
