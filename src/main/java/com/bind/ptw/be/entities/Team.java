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
@Table(name = "TEAMS")
public class Team {
	private Integer teamId;
	private String teamName;
	private String teamShortName;
	private Country country;
	private Integer countryId;
	private TeamType teamType;
	private Integer teamTypeId;
	private SportType sportType;
	private Integer sportTypeId;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TEAM_ID", unique = true, nullable = false)
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	@Column(name = "TEAM_NAME")
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Column(name = "TEAM_SHORT_NAME")
	public String getTeamShortName() {
		return teamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
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
	@JoinColumn(name = "TEAM_TYPE_ID", referencedColumnName = "TEAM_TYPE_ID")
	public TeamType getTeamType() {
		return teamType;
	}
	public void setTeamType(TeamType teamType) {
		this.teamType = teamType;
	}
	
	@Column(name = "TEAM_TYPE_ID", insertable = false, updatable = false)
	public Integer getTeamTypeId() {
		return teamTypeId;
	}
	public void setTeamTypeId(Integer teamTypeId) {
		this.teamTypeId = teamTypeId;
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
