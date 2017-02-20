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
@Table(name = "USER_SCOREBOARD")
public class UserScoreBoard {
	
	private Integer userScoreBoardId;
	private Users user;
	private Integer tournamentId;
	private Integer totalPoints;
	private Integer rank;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_SCOREBOARD_ID", unique = true, nullable = false)
	public Integer getUserScoreBoardId() {
		return userScoreBoardId;
	}
	public void setUserScoreBoardId(Integer userScoreBoardId) {
		this.userScoreBoardId = userScoreBoardId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = true)
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	@Column(name = "TOURNAMENT_ID")
	public Integer getTournamentId() {
		return this.tournamentId;
	}
	
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	@Column(name = "TOTAL_POINTS")
	public Integer getTotalPoints() {
		return totalPoints;
	}
	
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	@Column(name = "RANK")
	public Integer getRank() {
		return rank;
	}
	
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}
