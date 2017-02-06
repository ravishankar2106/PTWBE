package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.Date;

public class UserSelectedAnswerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userAnswerId;
	private int userId;
	private int questionId;
	private int selectedAnswerOptionId;
	private Date answerdDate;
	private Integer pointScored;
	
	public int getUserAnswerId() {
		return userAnswerId;
	}
	public void setUserAnswerId(int userAnswerId) {
		this.userAnswerId = userAnswerId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getSelectedAnswerOptionId() {
		return selectedAnswerOptionId;
	}
	public void setSelectedAnswerOptionId(int selectedAnswerOptionId) {
		this.selectedAnswerOptionId = selectedAnswerOptionId;
	}
	public Date getAnswerdDate() {
		return answerdDate;
	}
	public void setAnswerdDate(Date answerdDate) {
		this.answerdDate = answerdDate;
	}
	public Integer getPointScored() {
		return pointScored;
	}
	public void setPointScored(Integer pointScored) {
		this.pointScored = pointScored;
	}
	
	
}
