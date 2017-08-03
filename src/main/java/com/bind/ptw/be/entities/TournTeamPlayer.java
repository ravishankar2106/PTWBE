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
	private Integer tournamentteamId;
	private Player player;
	private Integer playerId;
	
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
	@JoinColumn(name = "TOURNAMENT_TEAM_ID", referencedColumnName = "TOURNAMENT_TEAM_ID")
	public TournamentTeam getTournamentTeam() {
		return tournamentTeam;
	}
	
	public void setTournamentTeam(TournamentTeam tournamentTeam) {
		this.tournamentTeam = tournamentTeam;
	}
	
	@Column(name = "TOURNAMENT_TEAM_ID", insertable = false, updatable = false)
	public Integer getTournamentteamId() {
		return tournamentteamId;
	}

	public void setTournamentteamId(Integer tournamentteamId) {
		this.tournamentteamId = tournamentteamId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID", referencedColumnName = "PLAYER_ID")
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Column(name = "PLAYER_ID", insertable = false, updatable = false)
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	
}
