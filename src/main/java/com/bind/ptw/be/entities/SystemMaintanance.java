package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEM_MAINTANANCE")
public class SystemMaintanance {
	
	private Integer systemMaintananceId;
	private Boolean status;
	
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SYSTEM_MAINTANANCE_ID", unique = true, nullable = false)
	public Integer getSystemMaintananceId() {
		return systemMaintananceId;
	}
	public void setSystemMaintananceId(Integer systemMaintananceId) {
		this.systemMaintananceId = systemMaintananceId;
	}
	
	@Column(name = "SYSTEM_MAINTANANCE_STATUS", unique = true, nullable = true)
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
