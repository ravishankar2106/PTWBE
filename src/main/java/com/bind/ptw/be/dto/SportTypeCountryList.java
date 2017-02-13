package com.bind.ptw.be.dto;

import java.util.List;

public class SportTypeCountryList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer sportTypeId;
	private List<CountryBean> countryBeanList;

	public Integer getSportTypeId() {
		return sportTypeId;
	}

	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}

	public List<CountryBean> getCountryBeanList() {
		return countryBeanList;
	}

	public void setCountryBeanList(List<CountryBean> countryBeanList) {
		this.countryBeanList = countryBeanList;
	}

	
}
