package com.bind.ptw.be.dto;

import java.io.Serializable;

public class AnswerOptionBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer answerOptionId;
	private Integer contestId;
	private String answerOptionStr;
	private Boolean correctAnswerFlag;
	private Integer points;
	
	public Integer getAnswerOptionId() {
		return answerOptionId;
	}
	public void setAnswerOptionId(Integer answerOptionId) {
		this.answerOptionId = answerOptionId;
	}
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	public String getAnswerOptionStr() {
		return answerOptionStr;
	}
	public void setAnswerOptionStr(String answerOptionStr) {
		this.answerOptionStr = answerOptionStr;
	}
	public Boolean getCorrectAnswerFlag() {
		return correctAnswerFlag;
	}
	public void setCorrectAnswerFlag(Boolean correctAnswerFlag) {
		this.correctAnswerFlag = correctAnswerFlag;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
		
}
