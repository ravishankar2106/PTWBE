package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TOURNAMENT_MASTER")
public class Tournament {

	private Integer tournamentId;
	private String tournamentName;
	private String tournamentVenue;
	private String tournamentDescription;
	private Date startDate;
	private Date endDate;
	private Date publishDate;
	private Boolean archievedFlag;
	private TeamType teamType;
	private Integer teamTypeId;
	private SportType sportType;
	private Integer sportTypeId;
	private String tournamentIconName;
	
	private Set<Match> tournamentMatches = new LinkedHashSet<Match>(
			0);
	private Integer tocGroupId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TOURNAMENT_ID", unique = true, nullable = false)
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	@Column(name = "TOURNAMENT_NAME")
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	
	@Column(name = "TOURNAMENT_VENUE")
	public String getTournamentVenue() {
		return tournamentVenue;
	}
	public void setTournamentVenue(String tournamentVenue) {
		this.tournamentVenue = tournamentVenue;
	}
	
	@Column(name = "TOURNAMENT_DESCRIPTION")
	public String getTournamentDescription() {
		return tournamentDescription;
	}
	public void setTournamentDescription(String tournamentDescription) {
		this.tournamentDescription = tournamentDescription;
	}
	
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "PUBLISH_DATE")
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	@Column(name = "ARCHIEVED_FLAG")
	public Boolean isArchievedFlag() {
		return archievedFlag;
	}

	public void setArchievedFlag(Boolean archievedFlag) {
		this.archievedFlag = archievedFlag;
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
	
	
	@Column(name = "TOURNAMENT_ICON_NAME")
	public String getTournamentIconName() {
		return tournamentIconName;
	}
	public void setTournamentIconName(String tournamentIconName) {
		this.tournamentIconName = tournamentIconName;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
	public Set<Match> getTournamentMatches() {
		return this.tournamentMatches;
	}
	
	public void setTournamentMatches(
			Set<Match> tournamentMatches) {
		this.tournamentMatches = tournamentMatches;
	}
	
	@Column(name = "TOC_GROUP_ID")
	public Integer getTocGroupId() {
		return tocGroupId;
	}
	
	public void setTocGroupId(Integer tocGroupId){
		this.tocGroupId = tocGroupId;
	}
}
