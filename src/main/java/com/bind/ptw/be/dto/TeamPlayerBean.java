package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TeamPlayerBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720686667705078667L;
	
	public Integer tourTeamPlayerId;
	public PlayerBean playerBean;
	
	public Integer getTourTeamPlayerId() {
		return tourTeamPlayerId;
	}
	public void setTourTeamPlayerId(Integer tourTeamPlayerId) {
		this.tourTeamPlayerId = tourTeamPlayerId;
	}
	public PlayerBean getPlayerBean() {
		return playerBean;
	}
	public void setPlayerBean(PlayerBean playerBean) {
		this.playerBean = playerBean;
	}
	
	

}
