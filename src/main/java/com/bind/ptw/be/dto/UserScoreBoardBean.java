package com.bind.ptw.be.dto;

import java.io.Serializable;

public class UserScoreBoardBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer tournamentId;
	private Integer pointsScored = 0;
	private Double cashWon;
	private Integer coinsWon;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Integer getPointsScored() {
		return pointsScored;
	}

	public void setPointsScored(Integer pointsScored) {
		this.pointsScored = pointsScored;
	}

	public Double getCashWon() {
		return cashWon;
	}

	public void setCashWon(Double cashWon) {
		this.cashWon = cashWon;
	}

	public Integer getCoinsWon() {
		return coinsWon;
	}

	public void setCoinsWon(Integer coinsWon) {
		this.coinsWon = coinsWon;
	}

}
