package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ContestBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer contestId;
	private String contestName;
	private Integer tournamentId;
	private Integer matchId;
	private String matchDateStr;
	private String teamNames;
	private String matchDisplayName;
	private Integer contestTypeId;
	private String contestTypeName;
	private Date publishStartDate;
	private String publishStartDateStr;
	private Date publishEndDate;
	private String publishEndDateStr;
	private Date cutoffDate;
	private String cutoffDateStr;
	private List<QuestionBean> questionList;
	private boolean pushNotified;
	private TournamentTeamBean teamABean;
	private TournamentTeamBean teamBBean;

	public Integer getTocId() {
		return tocId;
	}

	public void setTocId(Integer tocId) {
		this.tocId = tocId;
	}

	private Integer contestStatusId;
	private Integer bonusPoints;
	private Integer tocId;

	public Integer getContestId() {
		return contestId;
	}

	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}

	public String getContestName() {
		return contestName;
	}

	public void setContestName(String contestName) {
		this.contestName = contestName;
	}

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public String getMatchDateStr() {
		return matchDateStr;
	}

	public String getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(String teamNames) {
		this.teamNames = teamNames;
	}

	public void setMatchDateStr(String matchDateStr) {
		this.matchDateStr = matchDateStr;
	}

	public String getMatchDisplayName() {
		return matchDisplayName;
	}

	public void setMatchDisplayName(String matchDisplayName) {
		this.matchDisplayName = matchDisplayName;
	}

	public Integer getContestTypeId() {
		return contestTypeId;
	}

	public void setContestTypeId(Integer contestTypeId) {
		this.contestTypeId = contestTypeId;
	}

	public String getContestTypeName() {
		return contestTypeName;
	}

	public void setContestTypeName(String contestTypeName) {
		this.contestTypeName = contestTypeName;
	}

	public Date getPublishStartDate() {
		return publishStartDate;
	}

	public void setPublishStartDate(Date publishStartDate) {
		this.publishStartDate = publishStartDate;
	}

	public String getPublishStartDateStr() {
		return publishStartDateStr;
	}

	public void setPublishStartDateStr(String publishStartDateStr) {
		this.publishStartDateStr = publishStartDateStr;
	}

	public Date getPublishEndDate() {
		return publishEndDate;
	}

	public void setPublishEndDate(Date publishEndDate) {
		this.publishEndDate = publishEndDate;
	}

	public String getPublishEndDateStr() {
		return publishEndDateStr;
	}

	public void setPublishEndDateStr(String publishEndDateStr) {
		this.publishEndDateStr = publishEndDateStr;
	}

	public Date getCutoffDate() {
		return cutoffDate;
	}

	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

	public String getCutoffDateStr() {
		return cutoffDateStr;
	}

	public void setCutoffDateStr(String cutoffDateStr) {
		this.cutoffDateStr = cutoffDateStr;
	}

	public List<QuestionBean> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionBean> questionList) {
		this.questionList = questionList;
	}

	public Integer getContestStatusId() {
		return contestStatusId;
	}

	public void setContestStatusId(Integer contestStatusId) {
		this.contestStatusId = contestStatusId;
	}

	public Integer getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(Integer bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public boolean isPushNotified() {
		return pushNotified;
	}

	public void setPushNotified(boolean pushNotified) {
		this.pushNotified = pushNotified;
	}

	public TournamentTeamBean getTeamABean() {
		return teamABean;
	}

	public void setTeamABean(TournamentTeamBean teamABean) {
		this.teamABean = teamABean;
	}

	public TournamentTeamBean getTeamBBean() {
		return teamBBean;
	}

	public void setTeamBBean(TournamentTeamBean teamBBean) {
		this.teamBBean = teamBBean;
	}

}
