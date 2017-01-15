package com.bind.ptw.be.dto;

import java.util.List;

public class SportTypeBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<SportTypeBean> sportTypes;

	public List<SportTypeBean> getSportTypes() {
		return sportTypes;
	}

	public void setSportTypes(List<SportTypeBean> sportTypes) {
		this.sportTypes = sportTypes;
	}
	
	
	
}
