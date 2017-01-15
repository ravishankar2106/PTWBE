package com.bind.ptw.be.dto;

import java.io.Serializable;

public class AnswerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer answerId;
	private String answerStr;
	private Integer pointsAllocated;
	private Integer pointsScored=0;
	
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public String getAnswerStr() {
		return answerStr;
	}
	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}
	public Integer getPointsAllocated() {
		return pointsAllocated;
	}
	public void setPointsAllocated(Integer pointsAllocated) {
		this.pointsAllocated = pointsAllocated;
	}
	public Integer getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(Integer pointsScored) {
		this.pointsScored = pointsScored;
	}
	
}
