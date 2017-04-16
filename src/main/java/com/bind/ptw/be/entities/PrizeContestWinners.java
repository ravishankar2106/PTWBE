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
@Table(name = "PRIZE_CONTEST_WINNERS")
public class PrizeContestWinners {
	private Integer prizeContestWinnersId;
	private Users user;
	private Integer pointsScored;
	private PrizeContest prizeContest;
	private Integer rank;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PRIZE_CONTEST_WINNERS_ID", unique = true, nullable = false)
	public Integer getPrizeContestWinnersId() {
		return prizeContestWinnersId;
	}
	public void setPrizeContestWinnersId(Integer prizeContestWinnersId) {
		this.prizeContestWinnersId = prizeContestWinnersId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIZE_CONTEST_ID", nullable = true)
	public PrizeContest getPrizeContest() {
		return prizeContest;
	}
	
	public void setPrizeContest(PrizeContest prizeContest) {
		this.prizeContest = prizeContest;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = true)
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	@Column(name = "POINTS_SCORED")
	public Integer getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(Integer pointsScored) {
		this.pointsScored = pointsScored;
	}
	
	@Column(name = "RANK")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}
