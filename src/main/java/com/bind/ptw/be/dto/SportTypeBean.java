package com.bind.ptw.be.dto;

import java.io.Serializable;

public class SportTypeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888628800905334534L;
	
	private Integer sportTypeId;
	private String sportTypeName;
	
	public Integer getSportTypeId() {
		return sportTypeId;
	}
	public void setSportTypeId(Integer sportTypeId) {
		this.sportTypeId = sportTypeId;
	}
	public String getSportTypeName() {
		return sportTypeName;
	}
	public void setSportTypeName(String sportTypeName) {
		this.sportTypeName = sportTypeName;
	}
	
	
	
}
