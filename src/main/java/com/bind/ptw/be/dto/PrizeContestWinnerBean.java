package com.bind.ptw.be.dto;

import java.io.Serializable;

public class PrizeContestWinnerBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer prizeContestWinnerId;
	private Integer prizeContestId;
	private Integer userId;
	private Integer pointsScored;
	private Integer rank;
	private String teamName;
	private String userName;
	public Integer getPrizeContestWinnerId() {
		return prizeContestWinnerId;
	}
	public void setPrizeContestWinnerId(Integer prizeContestWinnerId) {
		this.prizeContestWinnerId = prizeContestWinnerId;
	}
	public Integer getPrizeContestId() {
		return prizeContestId;
	}
	public void setPrizeContestId(Integer prizeContestId) {
		this.prizeContestId = prizeContestId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(Integer pointsScored) {
		this.pointsScored = pointsScored;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
		
	
}
