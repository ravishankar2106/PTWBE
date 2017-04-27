package com.bind.ptw.be.dto;

import java.io.Serializable;

public class AnswerPulseBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String answerOptionName;
	private String answeredPercent;
	private Integer answerCount;
	
	public String getAnswerOptionName() {
		return answerOptionName;
	}
	public void setAnswerOptionName(String answerOptionName) {
		this.answerOptionName = answerOptionName;
	}
	public String getAnsweredPercent() {
		return answeredPercent;
	}
	public void setAnsweredPercent(String answeredPercent) {
		this.answeredPercent = answeredPercent;
	}
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	
	
}
