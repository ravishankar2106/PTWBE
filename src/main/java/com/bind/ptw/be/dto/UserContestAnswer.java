package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserContestAnswer extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private Integer contestId;
	private List<UserAnswerBean> userAnswerList;
	
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
	public List<UserAnswerBean> getUserAnswerList() {
		return userAnswerList;
	}
	public void setUserAnswerList(List<UserAnswerBean> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	
	
		
}
