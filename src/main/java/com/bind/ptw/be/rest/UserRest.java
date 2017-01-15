package com.bind.ptw.be.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.services.UserService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/user")
public class UserRest {
	
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public UserBean loginUser(@RequestBody UserBean inputUser){
		UserBean returnUserBean = userService.createUser(inputUser);
		return returnUserBean;
	}
	
	@PostMapping("/login")
    public UserBean authenticate(@RequestBody UserBean request) {
		UserBean response = userService.authenticateUser(request);
        return response;
    }
	
	@PostMapping("/confirmUser")
	public UserConfirmationBean confirmUser(@RequestBody UserConfirmationBean userConfirmationBean){
		UserConfirmationBean response = userService.confirmUser(userConfirmationBean);
		return response;
	}
	
	@GetMapping("/cities")
	public CityBeanList getCities(){
		CityBeanList response = userService.getCities();
		return response;
	}

}
