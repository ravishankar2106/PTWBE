package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserGroupBeanList extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<UserGroupBean> userGroups;

	public List<UserGroupBean> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroupBean> userGroups) {
		this.userGroups = userGroups;
	}
	
	
}
