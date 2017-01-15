package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class UserAnswerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer questionId;
	private List<AnswerBean> selectedAnswerList;
	private Integer pointsScored;
	
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	public List<AnswerBean> getSelectedAnswerList() {
		return selectedAnswerList;
	}
	public void setSelectedAnswerList(List<AnswerBean> selectedAnswerList) {
		this.selectedAnswerList = selectedAnswerList;
	}
	public Integer getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(Integer pointsScored) {
		this.pointsScored = pointsScored;
	}
	
		
}
