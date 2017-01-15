package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEST_STATUS")
public class ContestStatus {
	private Integer contestStatusId;
	private String contestStatusName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CONTEST_STATUS_ID", unique = true, nullable = false)
	public Integer getContestStatusId() {
		return contestStatusId;
	}
	public void setContestStatusId(Integer contestStatusId) {
		this.contestStatusId = contestStatusId;
	}
	
	@Column(name = "CONTEST_STATUS_NAME")
	public String getContestStatusName() {
		return contestStatusName;
	}
	public void setContestStatusName(String contestStatusName) {
		this.contestStatusName = contestStatusName;
	}
	
}
