
package com.bind.ptw.be.security;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

// TODO: Auto-generated Javadoc
/**
 * The Class TokenProvider.
 */
@Component
@PropertySource(value = {"classpath:application.properties"})
@SuppressWarnings("unchecked")
public class TokenProvider {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	/** The Constant AUTHORITIES_KEY. */
	private static final String SCOPES = "scopes";

	/** The token validity in milliseconds. */
	@Value("${security.authentication.jwt.tokenValidity: 86400000}")
	private Long tokenValidityInMilliseconds;

	/** The token validity in milliseconds for remember me. */
	@Value("${security.authentication.jwt.tokenValidityRememberMe: 86400000}")
	private Long tokenValidityInMillisecondsForRememberMe;

	@Value("${security.authentication.jwt.secret: fe717189668e381314aa57f997286f8f51dbfe9c}")
	private String secretKey;

	@Value("${security.authentication.jwt.tokenIssuer: bind.com}")
	private String tokenIssuer;

	/**
	 * Instantiates a new token provider.
	 *
	 * @param jhipsterProperties the jhipster properties
	 */
	public TokenProvider() {
		log.info("Token provider created: {}", this);
	}

	/**
	 * Creates the token.
	 *
	 * @param authentication the authentication
	 * @param rememberMe the remember me
	 * @return the string
	 */
	public String createToken(Authentication authentication, Boolean rememberMe) {

		Claims claims = Jwts.claims().setSubject(authentication.getName());
		Date currentTime = new Date();
		claims.put(SCOPES, authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentTime);
		if (rememberMe) {
			cal.add(Calendar.MILLISECOND, tokenValidityInMillisecondsForRememberMe.intValue());
		} else {
			cal.add(Calendar.MILLISECOND, tokenValidityInMilliseconds.intValue());
		}
		JwtBuilder tokenBuilder = Jwts.builder()
				.setClaims(claims)
				.setIssuer(tokenIssuer);
		if(rememberMe) {
			tokenBuilder.setId(UUID.randomUUID().toString());
		}
		tokenBuilder.setIssuedAt(currentTime)
				.setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, secretKey);
		return tokenBuilder.compact();
	}

	/**
	 * Gets the authentication.
	 *
	 * @param token the token
	 * @return the authentication
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		List<String> scopes = (List<String>) claims.get(SCOPES);
		Collection<? extends GrantedAuthority> authorities =
				scopes.stream()
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	/**
	 * Validate token.
	 *
	 * @param authToken the auth token
	 * @return true, if successful
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.info("Invalid JWT signature: " + e.getMessage());
			return false;
		}
	}

	@Override
	public String toString() {
		return "TokenProvider [tokenValidityInMilliseconds=" + tokenValidityInMilliseconds
				+ ", tokenValidityInMillisecondsForRememberMe=" + tokenValidityInMillisecondsForRememberMe
				+ ", secretKey=" + secretKey + ", tokenIssuer=" + tokenIssuer + "]";
	}
}
