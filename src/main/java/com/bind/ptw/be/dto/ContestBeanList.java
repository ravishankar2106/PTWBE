package com.bind.ptw.be.dto;

import java.util.List;

public class ContestBeanList extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private List<ContestBean> contestBeanList;

	public List<ContestBean> getContestBeanList() {
		return contestBeanList;
	}

	public void setContestBeanList(List<ContestBean> contestBeanList) {
		this.contestBeanList = contestBeanList;
	}

}
