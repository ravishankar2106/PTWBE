package com.bind.ptw.be.dto;

import java.util.List;

public class CityBeanList extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CityBean> cities;

	public List<CityBean> getCities() {
		return cities;
	}

	public void setCities(List<CityBean> cities) {
		this.cities = cities;
	}
	

}
