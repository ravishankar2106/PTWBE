package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PrizeContestBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer prizeContestId;
	private String prizeContestName;
	private Date startDate;
	private Date endDate;
	private String startDateStr;
	private String endDateStr;
	private Integer tournamentId;
	private Integer winnerSize;
	private List<GroupTeamBean> winnersTeamBeanList;
	private Boolean archieved;
	private Integer groupId;
	
	public Integer getPrizeContestId() {
		return prizeContestId;
	}
	public void setPrizeContestId(Integer prizeContestId) {
		this.prizeContestId = prizeContestId;
	}
	public String getPrizeContestName() {
		return prizeContestName;
	}
	public void setPrizeContestName(String prizeContestName) {
		this.prizeContestName = prizeContestName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Integer getWinnerSize() {
		return winnerSize;
	}
	public void setWinnerSize(Integer winnerSize) {
		this.winnerSize = winnerSize;
	}
	public List<GroupTeamBean> getWinnersTeamBeanList() {
		return winnersTeamBeanList;
	}
	public void setWinnersTeamBeanList(List<GroupTeamBean> winnersTeamBeanList) {
		this.winnersTeamBeanList = winnersTeamBeanList;
	}
	public Boolean getArchieved() {
		return archieved;
	}
	public void setArchieved(Boolean archieved) {
		this.archieved = archieved;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
		
	
}
