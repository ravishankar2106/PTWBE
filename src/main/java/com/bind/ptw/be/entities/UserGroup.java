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
	private Integer tournamentId;
	private Users ownerUser;
	private Integer ownerUserId;
	private String userGroupName;
	private Integer userGroupCode;
	private Long groupPoints;
	private Integer groupRank;
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
	@JoinColumn(name = "TOURNAMENT_ID", columnDefinition = "TOURNAMENT_ID")
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	@Column(name = "TOURNAMENT_ID", insertable = false, updatable = false)	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_USER_ID", referencedColumnName = "USER_ID")
	public Users getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(Users ownerUser) {
		this.ownerUser = ownerUser;
	}
	
	@Column(name = "OWNER_USER_ID", insertable = false, updatable = false)
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
	
	@Column(name = "GROUP_POINTS")
	public Long getGroupPoints() {
		return groupPoints;
	}
	
	public void setGroupPoints(Long groupPoints) {
		this.groupPoints = groupPoints;
	}
	
	@Column(name = "GROUP_RANK")
	public Integer getGroupRank() {
		return groupRank;
	}
	
	public void setGroupRank(Integer groupRank) {
		this.groupRank = groupRank;
	}
	
}
