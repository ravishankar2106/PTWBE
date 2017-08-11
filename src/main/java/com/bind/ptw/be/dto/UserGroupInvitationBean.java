package com.bind.ptw.be.dto;

import java.io.Serializable;

public class UserGroupInvitationBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userGroupInvitationId;
	private Integer groupId;
	private Integer groupOwnerId ;
	private String groupOwnerName;
	private String groupName;
	private Integer groupCode;
	private Integer inviteeUserId;
	private String phone;
	private String emailId;
	private Integer invitationStatusId;
	private String invitationStatusName;
	private Integer tournamentId;
	private String tournamentName;
	private String tournamentShortName;
	
	public Integer getUserGroupInvitationId() {
		return userGroupInvitationId;
	}
	public void setUserGroupInvitationId(Integer userGroupInvitationId) {
		this.userGroupInvitationId = userGroupInvitationId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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
	public Integer getGroupOwnerId() {
		return groupOwnerId;
	}
	public void setGroupOwnerId(Integer groupOwnerId) {
		this.groupOwnerId = groupOwnerId;
	}
	public String getGroupOwnerName() {
		return groupOwnerName;
	}
	public void setGroupOwnerName(String groupOwnerName) {
		this.groupOwnerName = groupOwnerName;
	}
	public Integer getInviteeUserId() {
		return inviteeUserId;
	}
	public void setInviteeUserId(Integer inviteeUserId) {
		this.inviteeUserId = inviteeUserId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Integer getInvitationStatusId() {
		return invitationStatusId;
	}
	public void setInvitationStatusId(Integer invitationStatusId) {
		this.invitationStatusId = invitationStatusId;
	}
	public String getInvitationStatusName() {
		return invitationStatusName;
	}
	public void setInvitationStatusName(String invitationStatusName) {
		this.invitationStatusName = invitationStatusName;
	}
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	public String getTournamentShortName() {
		return tournamentShortName;
	}
	public void setTournamentShortName(String tournamentShortName) {
		this.tournamentShortName = tournamentShortName;
	}
	

}
