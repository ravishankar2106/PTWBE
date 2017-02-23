package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserGroupBeanList extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<UserGroupBean> userGroupBean;

	public List<UserGroupBean> getUserGroupBean() {
		return userGroupBean;
	}

	public void setUserGroupBean(List<UserGroupBean> userGroupBean) {
		this.userGroupBean = userGroupBean;
	}
	
	
}
