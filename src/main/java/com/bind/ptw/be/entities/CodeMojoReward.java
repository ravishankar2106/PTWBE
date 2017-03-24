package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CODE_MOJO_REWARDS")
public class CodeMojoReward {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CODE_MOJO_REWARD_ID", unique = true, nullable = false)
	private Integer codeMojoRewardId;
	
	@Column(name = "CUSTOMER_ID")
	private String customerId;

	@Column(name = "COMM_EMAIL")
	private String communicationEmailId;
	
	@Column(name = "COMM_PHONE")
	private String communicationPhone;
	
	@Column(name = "HASH_VAL")
	private String hashVal;
	
	@Column(name = "TXN_ID")
	private String transactionId;
	
	@Column(name = "COUPON_CODE")
	private String couponCode;
	
	@Column(name = "BRAND_NAME")
	private String brandName;
	
	@Column(name = "BRAND_URL")
	private String brandUrl;
	
	@Column(name = "LOGO")
	private String logo;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "FINEPRINT")
	private String finePrint;
	
	@Column(name = "REDEEM_PROCESS")
	private String redeemProcess;
	
	@Column(name = "SUPPORT")
	private String support;
	
	@Column(name = "VAL_FORMATTED")
	private String valFormatter;
	
	@Column(name = "VAL_NUMERIC")
	private String valNumeric;
	
	@Column(name = "VAL_RATION")
	private String valRation;
	
	@Column(name = "VALIDITY_STAMP")
	private String valStamp;
	
	@Column(name = "VALIDITY")
	private String validity;

	public Integer getCodeMojoRewardId() {
		return codeMojoRewardId;
	}

	public void setCodeMojoRewardId(Integer codeMojoRewardId) {
		this.codeMojoRewardId = codeMojoRewardId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCommunicationEmailId() {
		return communicationEmailId;
	}

	public void setCommunicationEmailId(String communicationEmailId) {
		this.communicationEmailId = communicationEmailId;
	}

	public String getCommunicationPhone() {
		return communicationPhone;
	}

	public void setCommunicationPhone(String communicationPhone) {
		this.communicationPhone = communicationPhone;
	}

	public String getHashVal() {
		return hashVal;
	}

	public void setHashVal(String hashVal) {
		this.hashVal = hashVal;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandUrl() {
		return brandUrl;
	}

	public void setBrandUrl(String brandUrl) {
		this.brandUrl = brandUrl;
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

	public String getFinePrint() {
		return finePrint;
	}

	public void setFinePrint(String finePrint) {
		this.finePrint = finePrint;
	}

	public String getRedeemProcess() {
		return redeemProcess;
	}

	public void setRedeemProcess(String redeemProcess) {
		this.redeemProcess = redeemProcess;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getValFormatter() {
		return valFormatter;
	}

	public void setValFormatter(String valFormatter) {
		this.valFormatter = valFormatter;
	}

	public String getValNumeric() {
		return valNumeric;
	}

	public void setValNumeric(String valNumeric) {
		this.valNumeric = valNumeric;
	}

	public String getValRation() {
		return valRation;
	}

	public void setValRation(String valRation) {
		this.valRation = valRation;
	}

	public String getValStamp() {
		return valStamp;
	}

	public void setValStamp(String valStamp) {
		this.valStamp = valStamp;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}
	
	
	
	
	
	
	
	
}
