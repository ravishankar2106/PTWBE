package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserGroupInvitationBeanList extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<UserGroupInvitationBean> userGroupInvitations;

	public List<UserGroupInvitationBean> getUserInvitationGroups() {
		return userGroupInvitations;
	}

	public void setUserInvitationGroup(List<UserGroupInvitationBean> userGroupInvitations) {
		this.userGroupInvitations = userGroupInvitations;
	}
	
	
}
