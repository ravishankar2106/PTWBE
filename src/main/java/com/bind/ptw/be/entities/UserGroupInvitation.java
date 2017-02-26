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
@Table(name = "USER_GROUP_INVITATIONS")
public class UserGroupInvitation {
	private Integer userGroupInvitationId;
	private UserGroup userGroup;
	private Integer inviteeUserId;
	private String phone;
	private String emailId;
	private UserInvitationStatus userInvitationStatus;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_GROUP_INVITATIONS_ID", unique = true, nullable = false)
	public Integer getUserGroupInvitationId() {
		return userGroupInvitationId;
	}
	public void setUserGroupInvitationId(Integer userGroupInvitationId) {
		this.userGroupInvitationId = userGroupInvitationId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_GROUP_ID", nullable = true)
	public UserGroup getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
	@Column(name = "INVITEE_USER_ID")
	public Integer getInviteeUserId() {
		return inviteeUserId;
	}
	public void setInviteeUserId(Integer inviteeUserId) {
		this.inviteeUserId = inviteeUserId;
	}
	
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_INVITATION_STATUS_ID", nullable = true)
	public UserInvitationStatus getUserInvitationStatus() {
		return userInvitationStatus;
	}
	public void setUserInvitationStatus(UserInvitationStatus userInvitationStatus) {
		this.userInvitationStatus = userInvitationStatus;
	}
	
	
}
