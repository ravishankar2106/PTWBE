package com.bind.ptw.be.dto;

import java.util.List;

public class UserTournamentBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<UserTournamentBean> userTournamentList;
	private UserBean userBean;

	public List<UserTournamentBean> getUserTournamentList() {
		return userTournamentList;
	}

	public void setUserTournamentList(List<UserTournamentBean> userTournamentList) {
		this.userTournamentList = userTournamentList;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
