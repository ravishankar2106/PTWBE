package com.bind.ptw.be.dto;

import java.util.List;

public class CountryBeanList extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2887351832281734956L;
	private List<CountryBean> countries;
	public List<CountryBean> getCountries() {
		return countries;
	}
	public void setCountries(List<CountryBean> countries) {
		this.countries = countries;
	}

}
