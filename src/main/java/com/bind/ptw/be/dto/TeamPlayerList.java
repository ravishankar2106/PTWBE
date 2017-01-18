package com.bind.ptw.be.dto;

import java.util.List;

public class TeamPlayerList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer teamId;
	private List<Integer> playerIdList;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public List<Integer> getPlayerIdList() {
		return playerIdList;
	}

	public void setPlayerIdList(List<Integer> playerIdList) {
		this.playerIdList = playerIdList;
	}
	
	
}
