package com.bind.ptw.be.dto;

import java.util.List;

public class SportTypeCountryList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer sportTypeId;
	private List<Integer> countryIdList;

	public Integer getSportTypeId() {
		return sportTypeId;
	}

	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}

	public List<Integer> getCountryIdList() {
		return countryIdList;
	}

	public void setCountryIdList(List<Integer> countryIdList) {
		this.countryIdList = countryIdList;
	}
	
	
}
