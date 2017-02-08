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
@Table(name = "USERS_TOURNAMENT_REGISTRATIONS")
public class UserTournamentRegistration {
	private Integer userTournamentRegistrationId;
	private Integer userId;
	private Tournament tournament;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USERS_TOURNAMENT_REGISTRATION_ID", unique = true, nullable = false)
	public Integer getUserTournamentRegistrationId() {
		return userTournamentRegistrationId;
	}
	public void setUserTournamentRegistrationId(Integer userTournamentRegistrationId) {
		this.userTournamentRegistrationId = userTournamentRegistrationId;
	}
	
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_ID", nullable = true)
	public Tournament getTournament() {
		return tournament;
	}
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	
	
}
