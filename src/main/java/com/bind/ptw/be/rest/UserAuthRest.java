package com.bind.ptw.be.rest;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.UserService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/userAuthRest")
public class UserAuthRest {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	ContestService contestService;
	
	@Autowired
	TournamentService tournamentService;
	
	@PostMapping("/register")
	public UserBean registerUser(@RequestBody UserBean inputUser){
		UserBean returnUserBean = userService.createUser(inputUser);
		return returnUserBean;
	}
	
	@PostMapping("/resetPassword")
	public BaseBean resetPassword(@RequestBody UserBean userBean){
		BaseBean response = userService.resetPassword(userBean);
		return response;
	}
	
	@GetMapping("/cities")
	public CityBeanList getCities(){
		CityBeanList response = userService.getCities();
		return response;
	}
	
	@PostMapping("/login")
    public UserBean authenticate(@RequestBody UserBean request, HttpServletResponse httpResponse) {
		UserBean response = userService.authenticateUser(request, false);
        return response;
    }
	
	
}
