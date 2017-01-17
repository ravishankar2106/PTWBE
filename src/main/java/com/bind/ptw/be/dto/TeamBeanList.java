package com.bind.ptw.be.dto;

import java.util.List;

public class TeamBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<TeamBean> teams;

	public List<TeamBean> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamBean> teams) {
		this.teams = teams;
	}
	
	
	
}
