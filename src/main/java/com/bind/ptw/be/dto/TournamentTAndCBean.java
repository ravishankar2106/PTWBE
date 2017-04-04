package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class TournamentTAndCBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3251804379673187445L;
	
	private Integer tournamentId;
	private Integer contestId;
	private Integer groupId;
	private List<String> termsText;
	
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
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public List<String> getTermsText() {
		return termsText;
	}
	public void setTermsText(List<String> termsText) {
		this.termsText = termsText;
	}
	
	
	
}
