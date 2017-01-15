package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TOURNAMENT_RULES")
public class TournamentRules {
	
	private Integer tournamentRulesId;
	private Integer tournamentId;
	private Integer contestId;
	private String rules;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TOURNAMENT_RULES_ID", unique = true, nullable = false)
	public Integer getTournamentRulesId() {
		return tournamentRulesId;
	}
	public void setTournamentRulesId(Integer tournamentRulesId) {
		this.tournamentRulesId = tournamentRulesId;
	}
	
	@Column(name = "TOURNAMENT_ID")
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	@Column(name = "CONTEST_ID")
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	
	@Column(name = "TOURNAMENT_RULES")
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	
}
