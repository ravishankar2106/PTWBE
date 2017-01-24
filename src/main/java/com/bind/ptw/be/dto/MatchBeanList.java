package com.bind.ptw.be.dto;

import java.util.List;

public class MatchBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer tournamentId;
	private List<MatchBean> matchBeanList;
	
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public List<MatchBean> getMatchBeanList() {
		return matchBeanList;
	}
	public void setMatchBeanList(List<MatchBean> matchBeanList) {
		this.matchBeanList = matchBeanList;
	}

	
	
}
