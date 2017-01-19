package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TournamentTeamBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tournamentTeamId;
	private Integer teamId;
	private String teamShortName;
	private String teamName;
	
	public Integer getTournamentTeamId() {
		return tournamentTeamId;
	}
	public void setTournamentTeamId(Integer tournamentTeamId) {
		this.tournamentTeamId = tournamentTeamId;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getTeamShortName() {
		return teamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
}
