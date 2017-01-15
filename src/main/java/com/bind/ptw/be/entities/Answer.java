package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ANSWER")
public class Answer {
	
	private Integer answerId;
	private Integer answerOptionId;
	private Question question;
	private String correctAnswer;
	private Integer points;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ANSWER_ID", unique = true, nullable = false)
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	
	@Column(name = "ANSWER_OPTIONS_ID", unique = true, nullable = true)
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
	
	@Column(name = "CORRECT_ANSWER")
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	@Column(name = "POINTS")
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
}
