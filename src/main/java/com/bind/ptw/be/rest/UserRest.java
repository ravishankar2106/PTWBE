package com.bind.ptw.be.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournamentBeanList;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.UserService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/user")
public class UserRest {
	
	
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
	
	@PostMapping("/getOngoingTournament")
	public TournamentBeanList getOngoingTournament (@RequestBody TournamentBean tournamentBean){
		return tournamentService.getOngoingTournament(tournamentBean);
	}
	
	@PostMapping("/registerToTournament")
	public BaseBean registerUserToTournament (@RequestBody UserTournmentRegisterBean userTournament){
		return userService.registerUserToTournament(userTournament);
	}
	
	@PostMapping("/getUserRegisteredTournament")
	public UserTournamentBeanList getUserRegisterTournament(@RequestBody UserBean userBean){
		return userService.getUserRegisterTournament(userBean);
	}
	
	
	@PostMapping("/getOngoingContest")
	public ContestBeanList getOngoingContest(@RequestBody ContestBean contestBean){
		return contestService.getOngoingContests(contestBean);
	}
	
	@PostMapping("/submitUserAnswer")
	public BaseBean submitUserAnswer(@RequestBody UserContestAnswer userContestAsnwer){
		return contestService.submitUserAnswer(userContestAsnwer);
	}
	
	@PostMapping("/getMatchQuestion")
	public QuestionBeanList getMatchQuestion(@RequestBody MatchBean matchBean){
		return contestService.getMatchQuestions(matchBean);
	}
	
	
	

	
}
