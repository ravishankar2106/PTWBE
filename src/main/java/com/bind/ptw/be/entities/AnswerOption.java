package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ANSWER_OPTIONS")
public class AnswerOption {
	
	private Integer answerOptionId;
	private Question question;
	private String answerOptionStr;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ANSWER_OPTIONS_ID", unique = true, nullable = false)
	public Integer getAnswerOptionId() {
		return answerOptionId;
	}
	public void setAnswerOptionId(Integer answerOptionId) {
		this.answerOptionId = answerOptionId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTEST_QUESTION_ID", nullable = true)
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	@Column(name = "ANSWER_OPTION_STR")
	public String getAnswerOptionStr() {
		return answerOptionStr;
	}
	
	public void setAnswerOptionStr(String answerOptionStr) {
		this.answerOptionStr = answerOptionStr;
	}
	
}
