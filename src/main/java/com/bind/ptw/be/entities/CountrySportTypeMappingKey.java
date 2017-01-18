package com.bind.ptw.be.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CountrySportTypeMappingKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112885868013563483L;
	
	@Column(name = "COUNTRY_ID", nullable = false)
	private Integer countryId;
	
	@Column(name = "SPORT_TYPE_ID", nullable = false)
	private Integer sportTypeId;
	
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	public Integer getSportTypeId() {
		return sportTypeId;
	}
	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}
	
	
}
