package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class QuestionBean extends BaseBean implements Serializable, Comparable<Object>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer questionId;
	private Integer contestId;
	private Integer questionSlNo;
	private Integer answerTypeId;
	private String answerType;
	private String question;
	private Integer answerCount;
	private List<AnswerOptionBean> answerOptionList;
	//private String pointsRule;
	
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	public Integer getAnswerTypeId() {
		return answerTypeId;
	}
	public void setAnswerTypeId(Integer answerTypeId) {
		this.answerTypeId = answerTypeId;
	}
	public Integer getQuestionSlNo() {
		return questionSlNo;
	}
	public void setQuestionSlNo(Integer questionSlNo) {
		this.questionSlNo = questionSlNo;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	public List<AnswerOptionBean> getAnswerOptionList() {
		return answerOptionList;
	}
	public void setAnswerOptionList(List<AnswerOptionBean> answerOptionList) {
		this.answerOptionList = answerOptionList;
	}
	
	/*public String getPointsRule() {
		return pointsRule;
	}
	public void setPointsRule(String pointsRule) {
		this.pointsRule = pointsRule;
	}*/
	@Override
	public int compareTo(Object o) {
		QuestionBean comparable = (QuestionBean)o;
		int returnVal = 0;
		if(this.questionSlNo > comparable.questionSlNo){
			returnVal = 1;
		}else if(this.questionSlNo < comparable.questionSlNo){
			returnVal = -1;
		}
		return returnVal;
	}
	
		
}
