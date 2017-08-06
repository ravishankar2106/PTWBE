package com.bind.ptw.be.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.AnswerPulseBeanList;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.LeaderBoardBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.OneSignalUserRegistrationBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.dto.TournamentFanClubBean;
import com.bind.ptw.be.dto.TournamentFanClubList;
import com.bind.ptw.be.dto.TournamentTAndCBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupBeanList;
import com.bind.ptw.be.dto.UserGroupInvitationBean;
import com.bind.ptw.be.dto.UserGroupInvitationBeanList;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournamentBeanList;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.UserService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/userauth")
public class UserAutRest {
	
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public UserBean registerUser(@RequestBody UserBean inputUser){
		UserBean returnUserBean = userService.createUser(inputUser);
		return returnUserBean;
	}
	
	@PostMapping("/login")
    public UserBean authenticate(@RequestBody UserBean request) {
		UserBean response = userService.authenticateUser(request, false);
        return response;
    }
	
	@PostMapping("/confirmUser")
	public UserConfirmationBean confirmUser(@RequestBody UserConfirmationBean userConfirmationBean){
		UserConfirmationBean response = userService.confirmUser(userConfirmationBean);
		return response;
	}
	
	@PostMapping("/resetPassword")
	public BaseBean resetPassword(@RequestBody UserBean userBean){
		BaseBean response = userService.resetPassword(userBean);
		return response;
	}
	
	@PostMapping("/updatePassword")
	public BaseBean updatePassword(@RequestBody UserPasswordBean userPasswordBean){
		BaseBean response = userService.updatePassword(userPasswordBean);
		return response;
	}
	
	@GetMapping("/cities")
	public CityBeanList getCities(){
		CityBeanList response = userService.getCities();
		return response;
	}
	
	@PostMapping("/registerToPush")
	public BaseBean registerUserToPush(@RequestBody OneSignalUserRegistrationBean registrationBean){
		return userService.registerUserToPush(registrationBean);
	}
	
}
