package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserTournmentRegisterBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private List<Integer> tournamentList;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<Integer> getTournamentList() {
		return tournamentList;
	}
	public void setTournamentList(List<Integer> tournamentList) {
		this.tournamentList = tournamentList;
	}
	

}
