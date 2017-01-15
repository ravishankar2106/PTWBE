package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TOURNAMENT_MATCH_MASTER")
public class Match {
	private Integer matchId;
	private Tournament tournament;
	private String matchNo;
	private Date matchDateTime;
	private String venue;
	private TournamentTeam teamA;
	private TournamentTeam teamB;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TOURNAMENT_MATCH_MASTER_ID", unique = true, nullable = false)
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_ID", nullable = false)
	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	@Column(name = "MATCH_NO")
	public String getMatchNo() {
		return matchNo;
	}
	public void setMatchNo(String matchNo) {
		this.matchNo = matchNo;
	}
	
	@Column(name = "MATCH_DATE_TIME")
	public Date getMatchDateTime() {
		return matchDateTime;
	}
	public void setMatchDateTime(Date matchDateTime) {
		this.matchDateTime = matchDateTime;
	}
	
	@Column(name = "VENUE")
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_A", nullable = false)
	public TournamentTeam getTeamA() {
		return teamA;
	}
	
	public void setTeamA(TournamentTeam teamA) {
		this.teamA = teamA;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_B", nullable = false)
	public TournamentTeam getTeamB() {
		return teamB;
	}
	
	public void setTeamB(TournamentTeam teamB) {
		this.teamB = teamB;
	}
	
}
