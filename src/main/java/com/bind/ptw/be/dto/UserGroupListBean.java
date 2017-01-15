package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserGroupListBean extends BaseBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<UserGroupBean> userGroupBeanList;

	public List<UserGroupBean> getUserGroupBeanList() {
		return userGroupBeanList;
	}

	public void setUserGroupBeanList(List<UserGroupBean> userGroupBeanList) {
		this.userGroupBeanList = userGroupBeanList;
	}
	

}
