package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ANSWER_STATS")
public class UserAnswerStats {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ANSWER_STATS_ID", unique = true, nullable = false)
	private Integer userAnswerStatusId;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "TOURNAMENT_ID", nullable = false)
	private Integer tournamentId;
	
	@Column(name = "CONTESTS", nullable = false)
	private String contestIds;
	
	
	@Column(name = "PRIZE_COUNT_REDEEMED")
	private Integer prizeCountRedeemed;

	public Integer getUserAnswerStatusId() {
		return userAnswerStatusId;
	}

	public void setUserAnswerStatusId(Integer userAnswerStatusId) {
		this.userAnswerStatusId = userAnswerStatusId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Integer tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getContestIds() {
		return contestIds;
	}

	public void setContestIds(String contestIds) {
		this.contestIds = contestIds;
	}

	public Integer getPrizeCountRedeemed() {
		return prizeCountRedeemed;
	}

	public void setPrizeCountRedeemed(Integer prizeCountRedeemed) {
		this.prizeCountRedeemed = prizeCountRedeemed;
	}
	
	
}
