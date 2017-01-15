package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserGroupInvitationBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer invitationOwnerId ;
	private Integer groupId;
	private String groupName;
	private List<UserBean> userBeanList;
	
	public Integer getInvitationOwnerId() {
		return invitationOwnerId;
	}
	public void setInvitationOwnerId(Integer invitationOwnerId) {
		this.invitationOwnerId = invitationOwnerId;
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
	public List<UserBean> getUserBeanList() {
		return userBeanList;
	}
	public void setUserBeanList(List<UserBean> userBeanList) {
		this.userBeanList = userBeanList;
	}

}
