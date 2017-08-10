package com.bind.ptw.be.rest;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.security.JwtConfigurer;
import com.bind.ptw.be.security.TokenProvider;
import com.bind.ptw.be.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminRest {
	
	
	@Autowired
	UserService userService;
	
	/*@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	TokenProvider tokenProvider;
*/
	@PostMapping(value = "/login")
	public UserBean authenticate(@RequestBody @Valid UserBean request, HttpServletResponse httpResponse) {
		// authToken = new UsernamePasswordAuthenticationToken(request.getUserLoginId(), request.getPassword());
		UserBean response = userService.authenticateUser(request, true);
		/*try {
			Authentication authentication = this.authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.createToken(authentication, false);
			response.setToken(jwt);
			response.setRefreshToken(tokenProvider.createToken(authentication, true));
			httpResponse.addHeader(JwtConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		} catch(AuthenticationException ex) {
			response = null;
		}*/
		return response;
	}
	
}
