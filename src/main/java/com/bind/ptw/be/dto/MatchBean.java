package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.Date;

public class MatchBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer matchId;
	private String matchNo;
	private TournamentTeamBean teamA;
	private TournamentTeamBean teamB;
	private Date matchDateTime;
	private String matchDateTimeStr;
	private String venue;
	
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	public String getMatchNo() {
		return matchNo;
	}
	public void setMatchNo(String matchNo) {
		this.matchNo = matchNo;
	}
	public TournamentTeamBean getTeamA() {
		return teamA;
	}
	public void setTeamA(TournamentTeamBean teamA) {
		this.teamA = teamA;
	}
	public TournamentTeamBean getTeamB() {
		return teamB;
	}
	public void setTeamB(TournamentTeamBean teamB) {
		this.teamB = teamB;
	}
	public Date getMatchDateTime() {
		return matchDateTime;
	}
	public void setMatchDateTime(Date matchDateTime) {
		this.matchDateTime = matchDateTime;
	}
	public String getMatchDateTimeStr() {
		return matchDateTimeStr;
	}
	public void setMatchDateTimeStr(String matchDateTimeStr) {
		this.matchDateTimeStr = matchDateTimeStr;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}

}
