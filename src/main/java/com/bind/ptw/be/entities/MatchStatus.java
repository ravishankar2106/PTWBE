package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MATCH_STATUS")
public class MatchStatus {
	private Integer matchStatusId;
	private String matchStatusName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MATCH_STATUS_ID", unique = true, nullable = false)
	public Integer getMatchStatusId() {
		return matchStatusId;
	}
	public void setMatchStatusId(Integer matchStatusId) {
		this.matchStatusId = matchStatusId;
	}
	
	@Column(name = "MATCH_STATUS_NAME")
	public String getMatchStatusName() {
		return matchStatusName;
	}
	public void setMatchStatusName(String matchStatusName) {
		this.matchStatusName = matchStatusName;
	}
	
}
