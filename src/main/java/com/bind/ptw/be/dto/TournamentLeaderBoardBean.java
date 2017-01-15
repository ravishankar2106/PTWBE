package com.bind.ptw.be.dto;

import java.util.List;


public class TournamentLeaderBoardBean extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer tournamentId;
	private Integer groupId;
	private List<GroupTeamBean> groupTeamBeanList;
	
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public List<GroupTeamBean> getGroupTeamBeanList() {
		return groupTeamBeanList;
	}
	public void setGroupTeamBeanList(List<GroupTeamBean> groupTeamBeanList) {
		this.groupTeamBeanList = groupTeamBeanList;
	}
	
	
}
