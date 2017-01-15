package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONTEST_TYPE")
public class ContestType {
	private Integer contestTypeId;
	private String contestTypeName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CONTEST_TYPE_ID", unique = true, nullable = false)
	public Integer getContestTypeId() {
		return contestTypeId;
	}
	public void setContestTypeId(Integer contestTypeId) {
		this.contestTypeId = contestTypeId;
	}
	
	@Column(name = "CONTEST_TYPE_NAME")
	public String getContestTypeName() {
		return contestTypeName;
	}
	public void setContestTypeName(String contestTypeName) {
		this.contestTypeName = contestTypeName;
	}
	
}
