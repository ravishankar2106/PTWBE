package com.bind.ptw.be.dto;

import java.util.List;

public class TournamentFanClubList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer tournamentId;
	private String userId;
	private List<TournamentFanClubBean> fanClubs;
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	private Integer rank;
	
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<TournamentFanClubBean> getFanClubs() {
		return fanClubs;
	}
	public void setFanClubs(List<TournamentFanClubBean> fanClubs) {
		this.fanClubs = fanClubs;
	}
	
	
	
}
