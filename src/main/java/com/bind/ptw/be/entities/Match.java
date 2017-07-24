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
	private Integer tournamentId;
	private Integer matchNo;
	private Date matchDateTime;
	private String venue;
	private TournamentTeam teamA;
	private Integer teamAId;
	private TournamentTeam teamB;
	private Integer teamBId;
	private MatchStatus matchStatus;
	private Integer matchStatusId;
	
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
	@JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "TOURNAMENT_ID")
	public Tournament getTournament() {
		return this.tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	@Column(name = "TOURNAMENT_ID", insertable = false, updatable = false)
	public Integer getTournamentId() {
		return tournamentId;
	}
	
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	@Column(name = "MATCH_NO")
	public Integer getMatchNo() {
		return matchNo;
	}
	public void setMatchNo(Integer matchNo) {
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
	@JoinColumn(name = "TEAM_A", referencedColumnName = "TOURNAMENT_TEAM_ID")
	public TournamentTeam getTeamA() {
		return teamA;
	}
	
	public void setTeamA(TournamentTeam teamA) {
		this.teamA = teamA;
	}
	
	@Column(name = "TEAM_A", insertable = false, updatable = false)
	public Integer getTeamAId() {
		return teamAId;
	}
	public void setTeamAId(Integer teamAId) {
		this.teamAId = teamAId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_B", referencedColumnName = "TOURNAMENT_TEAM_ID")
	public TournamentTeam getTeamB() {
		return teamB;
	}
	
	public void setTeamB(TournamentTeam teamB) {
		this.teamB = teamB;
	}
	
	@Column(name = "TEAM_B", insertable = false, updatable = false)
	public Integer getTeamBId() {
		return teamBId;
	}
	public void setTeamBId(Integer teamBId) {
		this.teamBId = teamBId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MATCH_STATUS_ID", referencedColumnName = "MATCH_STATUS_ID")
	public MatchStatus getMatchStatus() {
		return matchStatus;
	}
	
	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}
	
	@Column(name = "MATCH_STATUS_ID", insertable = false, updatable = false)
	public Integer getMatchStatusId() {
		return matchStatusId;
	}
	public void setMatchStatusId(Integer matchStatusId) {
		this.matchStatusId = matchStatusId;
	}
	
	
	
}
