
package com.bind.ptw.be.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

//import io.jsonwebtoken.ExpiredJwtException;



/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a
 * valid user is found.
 */
public class JwtFilter {//extends GenericFilterBean {

 /* private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

  private TokenProvider tokenProvider;

  public JwtFilter(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    try {
      HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
      if (httpServletRequest != null && httpServletRequest.getPathInfo() != null && httpServletRequest.getPathInfo().contains("user"))  {
    	  httpServletRequest = null;
      }
      String jwt = resolveToken(httpServletRequest);
      if (StringUtils.hasText(jwt)) {
        if (this.tokenProvider.validateToken(jwt)) {
          Authentication authentication = this.tokenProvider.getAuthentication(jwt);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (ExpiredJwtException eje) {
      log.info("Security exception for user {} - {}", eje.getClaims().getSubject(),
          eje.getMessage());
      ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      ((HttpServletResponse) servletResponse).getWriter().write("{\"resultCode\": 401,\"resultDescription\": \"Token Expired\"}");
			((HttpServletResponse) servletResponse).getWriter().flush();
			((HttpServletResponse) servletResponse).getWriter().close();
			((HttpServletResponse) servletResponse).setContentType("application/json");
		
    }
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(JwtConfigurer.AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      String jwt = bearerToken.substring(7, bearerToken.length());
      return jwt;
    }
    String jwt = request.getParameter(JwtConfigurer.AUTHORIZATION_TOKEN);
    if (StringUtils.hasText(jwt)) {
      return jwt;
    }
    return null;
  }*/
}
