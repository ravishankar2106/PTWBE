package com.bind.ptw.be.dto;

import java.io.Serializable;

public class UserGroupBean extends BaseBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer groupId;
	private Integer tournamentId;
	private Integer ownerId;
	private String groupName;
	private Integer groupCode;
	private Boolean prizeGroupFlag;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}
	public Boolean getPrizeGroupFlag() {
		return prizeGroupFlag;
	}
	public void setPrizeGroupFlag(Boolean prizeGroupFlag) {
		this.prizeGroupFlag = prizeGroupFlag;
	}
	
	
	

}
