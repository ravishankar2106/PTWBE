package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserContestAnswer extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;
	private Integer contestId;
	private Integer bonusPoints;
	private List<UserAnswerBean> userAnswerList;
	private Integer totalPointsWon;
	private Double cashWon;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getContestId() {
		return contestId;
	}

	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}

	public Integer getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(Integer bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public List<UserAnswerBean> getUserAnswerList() {
		return userAnswerList;
	}

	public void setUserAnswerList(List<UserAnswerBean> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}

	public Integer getTotalPointsWon() {
		return totalPointsWon;
	}

	public void setTotalPointsWon(Integer totalPointsWon) {
		this.totalPointsWon = totalPointsWon;
	}

	public Double getCashWon() {
		return cashWon;
	}

	public void setCashWon(Double cashWon) {
		this.cashWon = cashWon;
	}

}
