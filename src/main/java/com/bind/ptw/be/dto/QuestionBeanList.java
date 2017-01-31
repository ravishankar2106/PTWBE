package com.bind.ptw.be.dto;

import java.util.List;

public class QuestionBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<QuestionBean> questionBeanList;

	public List<QuestionBean> getQuestionBeanList() {
		return questionBeanList;
	}

	public void setQuestionBeanList(List<QuestionBean> questionBeanList) {
		this.questionBeanList = questionBeanList;
	}


}
