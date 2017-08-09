package com.bind.ptw.be.services.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.UserBean;



/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

	@Autowired
	UserDao userDao;

	public DomainUserDetailsService() {
	}

	@Override
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		UserBean userFromDatabase = userDao.getUser(lowercaseLogin);

		if (null == userFromDatabase) {
			throw new UsernameNotFoundException("PTW Can't identify the user, may be you are trying wrong credentials !");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (userFromDatabase.isAdmin())authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		else authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new org.springframework.security.core.userdetails.User(userFromDatabase.getUserName(),
				userFromDatabase.getPassword(), authorities);

	}
}
