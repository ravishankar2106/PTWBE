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
		
}
