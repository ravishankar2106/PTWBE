package com.bind.ptw.be.dto;

import java.util.List;

public class LeaderBoardBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer tournamentId;
	private Integer groupId;
	private List<LeaderBoardBean> leaders;
	
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
	public List<LeaderBoardBean> getLeaders() {
		return leaders;
	}
	
	public void setLeaders(List<LeaderBoardBean> leaders) {
		this.leaders = leaders;
	}
	
	
}
