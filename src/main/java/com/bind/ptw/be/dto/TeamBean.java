package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TeamBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720686667705078667L;
	
	private Integer teamId;
	private String teamName;
	private String teamShortName;
	private Integer countryId;
	private String countryName;
	private Integer teamTypeId;
	private String teamTypeName;
	private Integer sportTypeId;
	private String sportTypeName;
	
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamShortName() {
		return teamShortName;
	}
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getTeamTypeId() {
		return teamTypeId;
	}
	public void setTeamTypeId(Integer teamTypeId) {
		this.teamTypeId = teamTypeId;
	}
	public String getTeamTypeName() {
		return teamTypeName;
	}
	public void setTeamTypeName(String teamTypeName) {
		this.teamTypeName = teamTypeName;
	}
	public Integer getSportTypeId() {
		return sportTypeId;
	}
	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}
	public String getSportTypeName() {
		return sportTypeName;
	}
	public void setSportTypeName(String sportTypeName) {
		this.sportTypeName = sportTypeName;
	}

}
