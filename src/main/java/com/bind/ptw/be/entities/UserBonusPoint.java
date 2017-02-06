package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_BONUS_POINTS")
public class UserBonusPoint {
	
	private Integer userBonusPointId;
	private Integer userId;
	private Integer contestId;
	private Integer points;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_BONUS_POINT_ID", unique = true, nullable = false)
	public Integer getUserBonusPointId() {
		return userBonusPointId;
	}
	public void setUserBonusPointId(Integer userBonusPointId) {
		this.userBonusPointId = userBonusPointId;
	}
	
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "CONTEST_ID")
	public Integer getContestId() {
		return contestId;
	}
	
	public void setContestId(Integer contestId) {
		this.contestId = contestId;
	}
	
	@Column(name = "POINTS_SCORED")
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	
}
