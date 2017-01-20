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
@Table(name = "TOURNAMENT_TEAM_PLAYERS")
public class TournTeamPlayer {
	
	private Integer tournTeamPlayerId;
	private TournamentTeam tournamentTeam;
	private Player player;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TOURNAMENT_TEAM_PLAYER_ID", unique = true, nullable = false)
	public Integer getTournTeamPlayerId() {
		return tournTeamPlayerId;
	}
	
	public void setTournTeamPlayerId(Integer tournTeamPlayerId) {
		this.tournTeamPlayerId = tournTeamPlayerId;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_TEAM_ID", nullable = false)
	public TournamentTeam getTournamentTeam() {
		return tournamentTeam;
	}
	
	public void setTournamentTeam(TournamentTeam tournamentTeam) {
		this.tournamentTeam = tournamentTeam;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID", nullable = false)
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
}
