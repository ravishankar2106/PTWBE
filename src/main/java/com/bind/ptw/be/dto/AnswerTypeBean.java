package com.bind.ptw.be.dto;

import java.io.Serializable;

public class AnswerTypeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720686667705078667L;
	
	public Integer answerTypeId;
	public String answerTypeName;
	public Integer getAnswerTypeId() {
		return answerTypeId;
	}
	public void setAnswerTypeId(Integer answerTypeId) {
		this.answerTypeId = answerTypeId;
	}
	public String getAnswerTypeName() {
		return answerTypeName;
	}
	public void setAnswerTypeName(String answerTypeName) {
		this.answerTypeName = answerTypeName;
	}
	
	
}
