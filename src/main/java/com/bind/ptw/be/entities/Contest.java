package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "CONTEST_MASTER")
public class Contest {
	private Integer contestId;
	private String contestName;
	private Match match;
	private Integer matchId;
	private Tournament tournament;
	private Integer tournamentId;
	private ContestType contestType;
	private Integer contestTypeId;
	private Date publishStartDateTime;
	private Date publishEndDateTime;
	private Date cutoffDateTime;
	private ContestStatus contestStatus;
	private Integer contestStatusId;
	private Integer bonusPoints;
	private List<Question> questionList = new ArrayList<Question>(
			0);
	private Integer tocGroupId;
	private Boolean pushNotified;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CONTEST_ID", unique = true, nullable = false)
	public Integer getContestId() {
		return contestId;
	}
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	
	@Column(name = "CONTEST_NAME")
	public String getContestName() {
		return contestName;
	}
	
	public void setContestName(String contestName) {
		this.contestName = contestName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "TOURNAMENT_ID")
	public Tournament getTournament() {
		return tournament;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOURNAMENT_MATCH_MASTER_ID", referencedColumnName = "TOURNAMENT_MATCH_MASTER_ID")
	public Match getMatch() {
		return match;
	}
	
	public void setMatch(Match match) {
		this.match = match;
	}
	
	@Column(name = "TOURNAMENT_MATCH_MASTER_ID", insertable = false, updatable = false)
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTEST_TYPE_ID", referencedColumnName = "CONTEST_TYPE_ID")
	public ContestType getContestType() {
		return contestType;
	}
	
	public void setContestType(ContestType contestType) {
		this.contestType = contestType;
	}
	
	@Column(name = "CONTEST_TYPE_ID", insertable = false, updatable = false)
	public Integer getContestTypeId() {
		return contestTypeId;
	}
	public void setContestTypeId(Integer contestTypeId) {
		this.contestTypeId = contestTypeId;
	}
	
	
	@Column(name = "PUBLISH_START_DATE_TIME")
	public Date getPublishStartDateTime() {
		return publishStartDateTime;
	}
	public void setPublishStartDateTime(Date publishStartDateTime) {
		this.publishStartDateTime = publishStartDateTime;
	}
	
	@Column(name = "PUBLISH_END_DATE_TIME")
	public Date getPublishEndDateTime() {
		return publishEndDateTime;
	}
	public void setPublishEndDateTime(Date publishEndDateTime) {
		this.publishEndDateTime = publishEndDateTime;
	}
	
	@Column(name = "CUTOFF_DATE_TIME")
	public Date getCutoffDateTime() {
		return cutoffDateTime;
	}
	public void setCutoffDateTime(Date cutoffDateTime) {
		this.cutoffDateTime = cutoffDateTime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTEST_STATUS_ID", referencedColumnName = "CONTEST_STATUS_ID")
	public ContestStatus getContestStatus() {
		return contestStatus;
	}
	
	public void setContestStatus(ContestStatus contestStatus) {
		this.contestStatus = contestStatus;
	}
	
	@Column(name = "CONTEST_STATUS_ID", insertable = false, updatable = false)
	public Integer getContestStatusId() {
		return contestStatusId;
	}
	public void setContestStatusId(Integer contestStatusId) {
		this.contestStatusId = contestStatusId;
	}
	
	@Column(name = "BONUS_POINTS")
	public Integer getBonusPoints() {
		return bonusPoints;
	}
	
	public void setBonusPoints(Integer bonusPoints) {
		this.bonusPoints = bonusPoints;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contest")
	public List<Question> getQuestion() {
		return this.questionList;
	}
	
	public void setQuestion(
			List<Question> questionList) {
		this.questionList = questionList;
	}
	
	@Column(name = "TOC_GROUP_ID")
	public Integer getTocGroupId() {
		return tocGroupId;
	}
	
	public void setTocGroupId(Integer tocGroupId) {
		this.tocGroupId = tocGroupId;
	}
	
	@Column(name = "PUSH_NOTIFIED")
	public Boolean getPushNotified() {
		return pushNotified;
	}
	public void setPushNotified(Boolean pushNotified) {
		this.pushNotified = pushNotified;
	}
	
	
}
