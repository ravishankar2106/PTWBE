package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TournamentFanClubBean extends BaseBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer groupId;
	private Integer tournamentId;
	private String groupName;
	private Boolean prizeGroupFlag;
	private Long clubPoints;
	private Integer clubPosition;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Boolean getPrizeGroupFlag() {
		return prizeGroupFlag;
	}
	public void setPrizeGroupFlag(Boolean prizeGroupFlag) {
		this.prizeGroupFlag = prizeGroupFlag;
	}
	public Long getClubPoints() {
		return clubPoints;
	}
	public void setClubPoints(Long clubPoints) {
		this.clubPoints = clubPoints;
	}
	public Integer getClubPosition() {
		return clubPosition;
	}
	public void setClubPosition(Integer clubPosition) {
		this.clubPosition = clubPosition;
	}

}
