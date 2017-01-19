package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class TournamentTeamBeanList extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tournamentId;
	private List<TournamentTeamBean> tournamentTeamBeanList;
	
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public List<TournamentTeamBean> getTournamentTeamBeanList() {
		return tournamentTeamBeanList;
	}
	public void setTournamentTeamBeanList(List<TournamentTeamBean> tournamentTeamBeanList) {
		this.tournamentTeamBeanList = tournamentTeamBeanList;
	}
	
	
	
	
}
