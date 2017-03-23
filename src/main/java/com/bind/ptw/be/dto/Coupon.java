package com.bind.ptw.be.dto;

import java.io.Serializable;

public class Coupon implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String txn_id;
	private String coupon_code;
	private String brand_name;
	private String brand_url;
	private String logo;
	private String title;
	private String fineprint;
	private String redemption_process;
	private String support;
	private String value_formatted;
	private String value_numeric;
	private String value_ratio;
	private String validity_stamp;
	private String validity;
	public String getTxn_id() {
		return txn_id;
	}
	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getBrand_url() {
		return brand_url;
	}
	public void setBrand_url(String brand_url) {
		this.brand_url = brand_url;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFineprint() {
		return fineprint;
	}
	public void setFineprint(String fineprint) {
		this.fineprint = fineprint;
	}
	public String getRedemption_process() {
		return redemption_process;
	}
	public void setRedemption_process(String redemption_process) {
		this.redemption_process = redemption_process;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	public String getValue_formatted() {
		return value_formatted;
	}
	public void setValue_formatted(String value_formatted) {
		this.value_formatted = value_formatted;
	}
	public String getValue_numeric() {
		return value_numeric;
	}
	public void setValue_numeric(String value_numeric) {
		this.value_numeric = value_numeric;
	}
	public String getValue_ratio() {
		return value_ratio;
	}
	public void setValue_ratio(String value_ratio) {
		this.value_ratio = value_ratio;
	}
	public String getValidity_stamp() {
		return validity_stamp;
	}
	public void setValidity_stamp(String validity_stamp) {
		this.validity_stamp = validity_stamp;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	
	
	
	
}
