package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ANSWER_TYPE")
public class AnswerType {
	private Integer answerTypeId;
	private String answerTypeName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ANSWER_TYPE_ID", unique = true, nullable = false)
	public Integer getAnswerTypeId() {
		return answerTypeId;
	}
	public void setAnswerTypeId(Integer answerTypeId) {
		this.answerTypeId = answerTypeId;
	}
	
	@Column(name = "ANSWER_TYPE_NAME")
	public String getAnswerTypeName() {
		return answerTypeName;
	}
	public void setAnswerTypeName(String answerTypeName) {
		this.answerTypeName = answerTypeName;
	}
	
}
