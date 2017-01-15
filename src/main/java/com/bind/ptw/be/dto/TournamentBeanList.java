package com.bind.ptw.be.dto;

import java.util.List;

public class TournamentBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<TournamentBean> tournamentList;

	public List<TournamentBean> getTournamentList() {
		return tournamentList;
	}

	public void setTournamentList(List<TournamentBean> tournamentList) {
		this.tournamentList = tournamentList;
	}

}
