package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_INVITATION_STATUS")
public class UserInvitationStatus {
	private Integer invitationStatusId;
	private String invitationStatusName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_INVITATION_STATUS_ID", unique = true, nullable = false)
	public Integer getInvitationStatusId() {
		return invitationStatusId;
	}
	public void setInvitationStatusId(Integer invitationStatusId) {
		this.invitationStatusId = invitationStatusId;
	}
	
	@Column(name = "USER_INVITATION_STATUS_NAME")
	public String getInvitationStatusName() {
		return invitationStatusName;
	}
	public void setInvitationStatusName(String invitationStatusName) {
		this.invitationStatusName = invitationStatusName;
	}
	
}
