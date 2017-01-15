package com.bind.ptw.be.dto;


public class UserTournamentBean extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private TournamentBean tournamentBean;
	private Integer totalPoints=0;
	private Integer rank;
	
	public TournamentBean getTournamentBean() {
		return tournamentBean;
	}
	public void setTournamentBean(TournamentBean tournamentBean) {
		this.tournamentBean = tournamentBean;
	}
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}
