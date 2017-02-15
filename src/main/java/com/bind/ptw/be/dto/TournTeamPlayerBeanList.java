package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class TournTeamPlayerBeanList extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tournamentTeamId;
	private List<TeamPlayerBean> teamPlayerBeanList;
	
	public Integer getTournamentTeamId() {
		return tournamentTeamId;
	}
	public void setTournamentTeamId(Integer tournamentTeamId) {
		this.tournamentTeamId = tournamentTeamId;
	}
	public List<TeamPlayerBean> getTeamPlayerBeanList() {
		return teamPlayerBeanList;
	}
	public void setTeamPlayerBeanList(List<TeamPlayerBean> teamPlayerBeanList) {
		this.teamPlayerBeanList = teamPlayerBeanList;
	}
	
	
}
