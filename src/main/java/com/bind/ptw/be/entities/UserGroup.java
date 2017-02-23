package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_GROUPS")
public class UserGroup {
	private Integer userGroupId;
	private Integer tournamentId;
	private Integer ownerUserId;
	private String userGroupName;
	private Integer userGroupCode;
	private Boolean prizeIncludedFlag;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_GROUP_ID", unique = true, nullable = false)
	public Integer getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	
	@Column(name = "TOURNAMENT_ID")
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	@Column(name = "USER_GROUP_NAME")
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	
	@Column(name = "OWNER_USER_ID")
	public Integer getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	
	@Column(name = "USER_GROUP_CODE")
	public Integer getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(Integer userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	
	@Column(name = "PRIZE_INCLUDED_FLAG")
	public Boolean getPrizeIncludedFlag() {
		return prizeIncludedFlag;
	}
	public void setPrizeIncludedFlag(Boolean prizeIncludedFlag) {
		this.prizeIncludedFlag = prizeIncludedFlag;
	}
	
	
	
}
