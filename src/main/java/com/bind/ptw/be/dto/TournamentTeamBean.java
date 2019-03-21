package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TournamentTeamBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer tournamentTeamId;
	private Integer teamId;
	private String teamShortName;
	private String teamName;
	private String teamIconName;

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

	public String getTeamIconName() {
		return teamIconName;
	}

	public void setTeamIconName(String teamIconName) {
		this.teamIconName = teamIconName;
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
