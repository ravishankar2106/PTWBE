package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TOC_MASTER")
public class TermsCondition {
	
	private Integer tocId;
	private Integer tocGroupId;
	private String tocText;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TOC_MASTER_ID", unique = true, nullable = false)
	public Integer getTocId() {
		return tocId;
	}
	public void setTocId(Integer tocId) {
		this.tocId = tocId;
	}
	
	@Column(name = "TOC_GROUP_ID")
	public Integer getTocGroupId() {
		return tocGroupId;
	}
	public void setTocGroupId(Integer tocGroupId) {
		this.tocGroupId = tocGroupId;
	}
	
	@Column(name = "TOC_TEXT")
	public String getTocText() {
		return tocText;
	}
	public void setTocText(String tocText) {
		this.tocText = tocText;
	}
	
	
	
}
