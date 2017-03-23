package com.bind.ptw.be.rest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CodeMojoRewardBean;
import com.bind.ptw.be.dto.Coupon;

@EnableAutoConfiguration
@RestController
@RequestMapping("/p2wreward")
public class CodeMojoRewardRest {
	
		
	@PostMapping("/rewardCallback")
	public BaseBean processRewardCallback(@RequestBody CodeMojoRewardBean rewardBean){
		System.out.println("Customer Id " + rewardBean.getCustomer_id());
		System.out.println("Email Id " + rewardBean.getCommunication_channel_email());
		System.out.println("Phone No " + rewardBean.getCommunication_channel_phone());
		System.out.println("Hash " + rewardBean.getHash());
		Coupon coupon = rewardBean.getCoupon();
		if(coupon == null){
			System.out.println("Coupon is null...");
		}else{
			System.out.println("Transaction Id " + coupon.getTxn_id());
			System.out.println("Coupon Code " + coupon.getCoupon_code());
			System.out.println("Brand Name " + coupon.getBrand_name());
			System.out.println("Brand URL " + coupon.getBrand_url());
			System.out.println("Logo " + coupon.getLogo());
			System.out.println("Title " + coupon.getTitle());
			System.out.println("Fineprint " + coupon.getFineprint());
			System.out.println("Redemption Process " + coupon.getRedemption_process());
			System.out.println("Support " + coupon.getSupport());
			System.out.println("Value Formatted " + coupon.getValue_formatted());
			System.out.println("Value Numeric " + coupon.getValue_numeric());
			System.out.println("Value Ratio " + coupon.getValue_ratio());
			System.out.println("Validity Stamp " + coupon.getValidity_stamp());
			System.out.println("Validity " + coupon.getValidity());
		}
		return new BaseBean();
	}
	
	
	
}
