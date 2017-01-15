package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TeamTypeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer teamTypeId;
	private String teamTypeName;
	
	public Integer getTeamTypeId() {
		return teamTypeId;
	}
	public void setTeamTypeId(Integer teamTypeId) {
		this.teamTypeId = teamTypeId;
	}
	public String getTeamTypeName() {
		return teamTypeName;
	}
	public void setTeamTypeName(String teamTypeName) {
		this.teamTypeName = teamTypeName;
	}
	
	
	
}
