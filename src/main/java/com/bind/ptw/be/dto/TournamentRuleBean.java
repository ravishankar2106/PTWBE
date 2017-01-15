package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class TournamentRuleBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3251804379673187445L;
	
	private Integer tournamentId;
	private Integer contestId;
	private List<String> rulesList;
	
	
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	public List<String> getRulesList() {
		return rulesList;
	}
	public void setRulesList(List<String> rulesList) {
		this.rulesList = rulesList;
	}
	
	
	

}
