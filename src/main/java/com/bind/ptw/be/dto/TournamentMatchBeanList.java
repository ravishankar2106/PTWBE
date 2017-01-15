package com.bind.ptw.be.dto;

import java.util.List;

public class TournamentMatchBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MatchBean> matchList;
	private Integer tournamentId;
	
	public List<MatchBean> getMatchList() {
		return matchList;
	}
	public void setMatchList(List<MatchBean> matchList) {
		this.matchList = matchList;
	}
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

}
