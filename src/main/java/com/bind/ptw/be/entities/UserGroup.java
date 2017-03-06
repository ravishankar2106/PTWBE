package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_GROUPS")
public class UserGroup {
	private Integer userGroupId;
	private Tournament tournament;
	private Users ownerUser;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_ID", nullable = true)
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	@Column(name = "USER_GROUP_NAME")
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_USER_ID", nullable = true)
	public Users getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(Users ownerUser) {
		this.ownerUser = ownerUser;
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
