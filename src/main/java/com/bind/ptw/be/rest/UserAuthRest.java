package com.bind.ptw.be.rest;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.LeaderBoardBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.OneSignalUserRegistrationBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.security.JwtConfigurer;
import com.bind.ptw.be.security.TokenProvider;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.UserService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/userauth")
public class UserAuthRest {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	TournamentService tournamentService;
	
	@Autowired
	ContestService contestService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	TokenProvider tokenProvider;
	
	@PostMapping("/register")
	public UserBean registerUser(@RequestBody UserBean inputUser, HttpServletResponse httpResponse){
		UserBean returnUserBean = userService.createUser(inputUser);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(inputUser.getUserLoginId(), inputUser.getPassword());
		Authentication authentication = this.authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication, false);
		returnUserBean.setToken(jwt);
		returnUserBean.setRefreshToken(tokenProvider.createToken(authentication, true));
		httpResponse.addHeader(JwtConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return returnUserBean;
	}
	
	@PostMapping("/login")
    public UserBean authenticate(@RequestBody UserBean request, HttpServletResponse httpResponse) {
		String rawPassword = request.getPassword();
		UserBean response = userService.authenticateUser(request, false);
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUserLoginId(), rawPassword);
			Authentication authentication = this.authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.createToken(authentication, false);
			response.setToken(jwt);
			response.setRefreshToken(tokenProvider.createToken(authentication, true));
			httpResponse.addHeader(JwtConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		} catch(AuthenticationException ex) {
			ex.printStackTrace();
			response = null;
		}
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
	
	@GetMapping("/getOngoingContest")
	public ContestBeanList getOngoingContest(){
		return contestService.getOngoingContests(new ContestBean());
	}
	
	@PostMapping("/getOngoingTournament")
	public TournamentBeanList getOngoingTournament (@RequestBody TournamentBean tournamentBean){
		return tournamentService.getOngoingTournament(tournamentBean);
	}
	
	@PostMapping("/submitAnswer")
	public BaseBean submitUserAnswer(@RequestBody UserContestAnswer userContestAsnwer){
		return contestService.submitUserAnswer(userContestAsnwer);
	}
	
	@PostMapping("/getMatchContest")
	public ContestBeanList getMatchContest(@RequestBody MatchBean matchBean){
		return contestService.getMatchContest(matchBean);
	}
	
	@PostMapping("/getMatchQuestion")
	public QuestionBeanList getMatchQuestion(@RequestBody MatchBean matchBean){
		return contestService.getMatchQuestions(matchBean);
	}
	
	@PostMapping("/getLeaderBoard")
	public LeaderBoardBeanList getLeaderBoard(@RequestBody LeaderBoardBeanList leaderBoardRequest){
		return contestService.getLeaderBoard(leaderBoardRequest);
	}
	
}
