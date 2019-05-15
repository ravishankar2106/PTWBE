package com.bind.ptw.be.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_COIN")
public class UserCoin {
	
	private Integer userCoinId;
	private Integer userId;
	private Integer coinAvailable;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_COIN_ID", unique = true, nullable = false)
	public Integer getUserCoinId() {
		return userCoinId;
	}
	public void setUserCoinId(Integer userCoinId) {
		this.userCoinId = userCoinId;
	}
	
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "COIN_AVAILABLE")
	public Integer getCoinAvailable() {
		return coinAvailable;
	}
	
	public void setCoinAvailable(Integer coinAvailable) {
		this.coinAvailable = coinAvailable;
	}
	
}
