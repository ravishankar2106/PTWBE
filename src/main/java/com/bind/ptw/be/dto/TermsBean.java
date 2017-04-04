package com.bind.ptw.be.dto;

import java.io.Serializable;

public class TermsBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tocId;
	private Integer tocGroupId;
	private String tocText;
	
	public Integer getTocId() {
		return tocId;
	}
	public void setTocId(Integer tocId) {
		this.tocId = tocId;
	}
	public Integer getTocGroupId() {
		return tocGroupId;
	}
	public void setTocGroupId(Integer tocGroupId) {
		this.tocGroupId = tocGroupId;
	}
	public String getTocText() {
		return tocText;
	}
	public void setTocText(String tocText) {
		this.tocText = tocText;
	}
	
	
}
