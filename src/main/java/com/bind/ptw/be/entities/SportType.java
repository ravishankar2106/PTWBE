package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPORTS_TYPE")
public class SportType {
	private Integer sportTypeId;
	private String sportTypeName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SPORT_TYPE_ID", unique = true, nullable = false)
	public Integer getSportTypeId() {
		return sportTypeId;
	}
	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}
	
	@Column(name = "SPORT_TYPE_NAME")
	public String getSportTypeName() {
		return sportTypeName;
	}
	public void setSportTypeName(String sportTypeName) {
		this.sportTypeName = sportTypeName;
	}
	
}
