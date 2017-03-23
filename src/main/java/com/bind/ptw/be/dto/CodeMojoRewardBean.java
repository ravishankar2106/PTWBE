package com.bind.ptw.be.dto;

import java.io.Serializable;

public class CodeMojoRewardBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customer_id;
	private String communication_channel_email;
	private String communication_channel_phone;
	private String hash;
	private Coupon coupon;
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCommunication_channel_email() {
		return communication_channel_email;
	}
	public void setCommunication_channel_email(String communication_channel_email) {
		this.communication_channel_email = communication_channel_email;
	}
	public String getCommunication_channel_phone() {
		return communication_channel_phone;
	}
	public void setCommunication_channel_phone(String communication_channel_phone) {
		this.communication_channel_phone = communication_channel_phone;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
	
}
