package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ANSWERS_BKP")
public class UserAnswerBkp {
	
	private Integer userAnswerBkpId;
	private Integer userId;
	private Integer questionId;
	private String userAnswer;
	private Date answeredDateTime;
	private Integer pointsScored;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ANSWERS_BKP_ID", unique = true, nullable = false)
	public Integer getUserAnswerBkpId() {
		return userAnswerBkpId;
	}
	public void setUserAnswerBkpId(Integer userAnswerBkpId) {
		this.userAnswerBkpId = userAnswerBkpId;
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
	
	@Column(name = "USER_ANSWER")
	public String getUserAnswer() {
		return userAnswer;
	}
	
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
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
}
