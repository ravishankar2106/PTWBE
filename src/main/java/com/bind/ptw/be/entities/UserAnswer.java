package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ANSWERS")
public class UserAnswer {
	
	private Integer userAnswerId;
	private Integer userId;
	private Integer questionId;
	private AnswerOption answerOption;
	private Integer answerOptionId;
	private Date answeredDateTime;
	private Integer pointsScored;
	private Double cashWon;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ANSWERS_ID", unique = true, nullable = false)
	public Integer getUserAnswerId() {
		return userAnswerId;
	}
	public void setUserAnswerId(Integer userAnswerId) {
		this.userAnswerId = userAnswerId;
	}
	
	@Column(name = "USER_ID", unique = true, nullable = true)
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "CONTEST_QUESTION_ID", nullable = true)
	public Integer getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SELECTED_ANSWER_OPTION_ID", referencedColumnName = "ANSWER_OPTIONS_ID")
	public AnswerOption getAnswerOption() {
		return answerOption;
	}
	
	public void setAnswerOption(AnswerOption answerOption) {
		this.answerOption = answerOption;
	}
	
	@Column(name = "SELECTED_ANSWER_OPTION_ID", insertable = false, updatable = false)	
	public Integer getAnswerOptionId() {
		return answerOptionId;
	}
	public void setAnswerOptionId(Integer answerOptionId) {
		this.answerOptionId = answerOptionId;
	}
	@Column(name = "ANSWERED_DATE_TIME")
	public Date getAnsweredDateTime() {
		return answeredDateTime;
	}
	
	public void setAnsweredDateTime(Date answeredDateTime) {
		this.answeredDateTime = answeredDateTime;
	}
	
	@Column(name = "POINTS_SCORED", unique = true, nullable = true)
	public Integer getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(Integer pointsScored) {
		this.pointsScored = pointsScored;
	}
	
	@Column(name = "CASH_WON", nullable = true)
	public Double getCashWon() {
		return cashWon;
	}
	
	public void setCashWon(Double cashWon) {
		this.cashWon = cashWon;
	}
}
