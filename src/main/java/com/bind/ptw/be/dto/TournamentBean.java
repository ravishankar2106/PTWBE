package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.Date;

public class TournamentBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3251804379673187445L;
	
	private Integer tournamentId;
	private String tournamentName;
	private Integer sportTypeId;
	private Integer teamTypeId;
	private String tournamentVenue;
	private String tournamentDesc;
	private Date startDate;
	private String startDateStr;
	private Date endDate;
	private String endDateStr;
	private Date publishDate;
	private String publishDateStr;
	private Integer tocId;
	
	//private Integer groupId;
	
	public Integer getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	public Integer getSportTypeId() {
		return sportTypeId;
	}
	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}
	public Integer getTeamTypeId() {
		return teamTypeId;
	}
	public void setTeamTypeId(Integer teamTypeId) {
		this.teamTypeId = teamTypeId;
	}
	public String getTournamentVenue() {
		return tournamentVenue;
	}
	public void setTournamentVenue(String tournamentVenue) {
		this.tournamentVenue = tournamentVenue;
	}
	public String getTournamentDesc() {
		return tournamentDesc;
	}
	public void setTournamentDesc(String tournamentDesc) {
		this.tournamentDesc = tournamentDesc;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	/*public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}*/
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishDateStr() {
		return publishDateStr;
	}
	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}
	public Integer getTocId() {
		return tocId;
	}
	public void setTocId(Integer tocId) {
		this.tocId = tocId;
	}
	
	
	

}
