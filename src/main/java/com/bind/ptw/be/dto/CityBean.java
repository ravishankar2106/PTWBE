package com.bind.ptw.be.dto;

import java.io.Serializable;

public class CityBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3720686667705078667L;
	
	public Integer cityId;
	public String cityName;
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
