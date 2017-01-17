package com.bind.ptw.be.dto;

import java.io.Serializable;

public class CountryBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720686667705078667L;
	
	public Integer countryId;
	public String countryName;
	public String countryShortName;
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
	public String getCountryShortName() {
		return countryShortName;
	}
	public void setCountryShortName(String countryShortName) {
		this.countryShortName = countryShortName;
	}
	
	

}
