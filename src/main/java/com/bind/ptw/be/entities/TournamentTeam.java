package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TOURNAMENT_TEAM")
public class TournamentTeam {
	private Integer tournamentTeamId;
	private Tournament tournament;
	private Team team;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TOURNAMENT_TEAM_ID", unique = true, nullable = false)
	public Integer getTournamentTeamId() {
		return tournamentTeamId;
	}
	
	public void setTournamentTeamId(Integer tournamentTeamId) {
		this.tournamentTeamId = tournamentTeamId;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_ID", nullable = false)
	public Tournament getTournament() {
		return tournament;
	}
	
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID", nullable = false)
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}
