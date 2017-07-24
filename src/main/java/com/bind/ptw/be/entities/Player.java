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
@Table(name = "PLAYERS_MASTER")
public class Player {
	private Integer playerId;
	private String firstName;
	private String lastName;
	private Country country;
	private Integer countryId;
	private SportType sportType;
	private Integer sportTypeId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PLAYER_ID", unique = true, nullable = false)
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	
	@Column(name = "PLAYER_FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "PLAYER_LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID")
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	@Column(name = "COUNTRY_ID", insertable = false, updatable = false)	
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPORT_TYPE_ID", referencedColumnName = "SPORT_TYPE_ID")
	public SportType getSportType() {
		return sportType;
	}
	public void setSportType(SportType sportType) {
		this.sportType = sportType;
	}
	
	@Column(name = "SPORT_TYPE_ID", insertable = false, updatable = false)
	public Integer getSportTypeId() {
		return sportTypeId;
	}
	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}
	
	
	
}
