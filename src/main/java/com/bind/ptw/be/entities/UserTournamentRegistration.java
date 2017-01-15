package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS_TOURNAMENT_REGISTRATIONS")
public class UserTournamentRegistration {
	private Integer userTournamentRegistrationId;
	private Integer userId;
	private Integer tournamentId;
	
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
	
	@Column(name = "TOURNAMENT_ID")
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	
	
}
