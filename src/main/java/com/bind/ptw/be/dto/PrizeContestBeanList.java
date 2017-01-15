package com.bind.ptw.be.dto;

import java.io.Serializable;
import java.util.List;

public class PrizeContestBeanList extends BaseBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List <PrizeContestBean> prizeContestBeanList;

	public List<PrizeContestBean> getPrizeContestBeanList() {
		return prizeContestBeanList;
	}

	public void setPrizeContestBeanList(List<PrizeContestBean> prizeContestBeanList) {
		this.prizeContestBeanList = prizeContestBeanList;
	}

	
	
}
