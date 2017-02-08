package com.bind.ptw.be.dto;

import java.util.List;

public class UserTournamentBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<UserTournamentBean> userTournamentList;
	private Integer userId;

	public List<UserTournamentBean> getUserTournamentList() {
		return userTournamentList;
	}

	public void setUserTournamentList(List<UserTournamentBean> userTournamentList) {
		this.userTournamentList = userTournamentList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

}
