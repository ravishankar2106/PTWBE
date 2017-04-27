package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class AnswerPulseBeanList extends BaseBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<AnswerPulseBean> answerPulses;

	public List<AnswerPulseBean> getAnswerPulses() {
		return answerPulses;
	}

	public void setAnswerPulses(List<AnswerPulseBean> answerPulses) {
		this.answerPulses = answerPulses;
	}
	
	
}
