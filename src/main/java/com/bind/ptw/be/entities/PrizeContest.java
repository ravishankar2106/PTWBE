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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRIZE_CONTEST")
public class PrizeContest {
	private Integer prizeContestId;
	private Integer tournamentId;
	private String prizeContestName;
	private Date startDate;
	private Date endDate;
	private Integer winnerSize;
	private Boolean processedFlag;
	private List<PrizeContestWinners> prizeContestWinners = new ArrayList<PrizeContestWinners>(
			0); 
	private Integer groupId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PRIZE_CONTEST_ID", unique = true, nullable = false)
	public Integer getPrizeContestId() {
		return prizeContestId;
	}
	public void setPrizeContestId(Integer prizeContestId) {
		this.prizeContestId = prizeContestId;
	}
	
	@Column(name = "TOURNAMENT_ID")
	public Integer getTournamentId() {
		return tournamentId;
	}
	
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	@Column(name = "PRIZE_CONTEST_NAME")
	public String getprizeContestName() {
		return prizeContestName;
	}
	
	public void setPrizeContestName(String prizeContestName) {
		this.prizeContestName = prizeContestName;
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
	
	@Column(name = "WINNERS_SIZE")
	public Integer getWinnerSize() {
		return winnerSize;
	}
	
	public void setWinnerSize(Integer winnerSize) {
		this.winnerSize = winnerSize;
	}
	
	@Column(name = "PROCESSED_FLAG")
	public Boolean getProcessedFlag() {
		return processedFlag;
	}
	
	public void setProcessedFlag(Boolean processedFlag) {
		this.processedFlag = processedFlag;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prizeContest")
	public List<PrizeContestWinners> getPrizeContestWinners() {
		return this.prizeContestWinners;
	}
	
	public void setPrizeContestWinners(
			List<PrizeContestWinners> prizeContestWinners) {
		this.prizeContestWinners = prizeContestWinners;
	}
	
	@Column(name = "GROUP_ID")
	public Integer getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
