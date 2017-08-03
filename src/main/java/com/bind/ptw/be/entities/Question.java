package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEST_QUESTION")
public class Question {
	
	private Integer questionId;
	private Contest contest;
	private Integer questionSlNo;
	private AnswerType answerType;
	private String questionDescription;
	private Integer answerCount;
	private List<AnswerOption> answerOptions = new ArrayList<AnswerOption>(
			0);
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CONTEST_QUESTION_ID", unique = true, nullable = false)
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTEST_ID", nullable = true)
	public Contest getContest() {
		return contest;
	}
	
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	@Column(name = "QUESTION_SL_NO")
	public Integer getQuestionSlNo() {
		return questionSlNo;
	}
	
	public void setQuestionSlNo(Integer questionSlNo) {
		this.questionSlNo = questionSlNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANSWER_TYPE_ID", nullable = true)
	public AnswerType getAnswerType() {
		return answerType;
	}
	
	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType;
	}
	
	@Column(name = "QUESTION_DESCRIPTION")
	public String getQuestionDescription() {
		return questionDescription;
	}
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}
	
	@Column(name = "ANSWERS_COUNT")
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	public List<AnswerOption> getAnswerOptions() {
		return this.answerOptions;
	}
	
	public void setAnswerOptions(
			List<AnswerOption> answerOptions) {
		this.answerOptions = answerOptions;
	}
		
		
}
