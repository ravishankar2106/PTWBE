package com.bind.ptw.be.dto;

import java.util.List;

public class TeamTypeBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<TeamTypeBean> teamTypes;

	public List<TeamTypeBean> getTeamTypes() {
		return teamTypes;
	}

	public void setTeamTypes(List<TeamTypeBean> teamTypes) {
		this.teamTypes = teamTypes;
	}
	
	
	
}
