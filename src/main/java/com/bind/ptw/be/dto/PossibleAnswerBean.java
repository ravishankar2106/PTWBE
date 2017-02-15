package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class PossibleAnswerBean extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> possibleAnswerList;

	public List<String> getPossibleAnswerList() {
		return possibleAnswerList;
	}

	public void setPossibleAnswerList(List<String> possibleAnswerList) {
		this.possibleAnswerList = possibleAnswerList;
	}
	
}
