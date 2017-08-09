
package com.bind.ptw.be.initializers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.bind.ptw.be.security.JwtConfigurer;
import com.bind.ptw.be.security.TokenProvider;
import com.bind.ptw.be.security.utils.Http401UnauthorizedEntryPoint;



// TODO: Auto-generated Javadoc
/**
 * The Class SecurityConfiguration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationManagerBuilder authenticationManagerBuilder;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private CorsFilter corsFilter;

  /**
   * Instantiates a new security configuration.
   *
   * @param authenticationManagerBuilder the authentication manager builder
   * @param userDetailsService the user details service
   * @param tokenProvider the token provider
   * @param corsFilter the cors filter
   */
  public SecurityConfiguration() {
  }

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
    try {
      authenticationManagerBuilder.userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("Security configuration failed", e);
    }
  }

  /**
   * Http 401 unauthorized entry point.
   *
   * @return the http 401 unauthorized entry point
   */
  @Bean
  public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint() {
    return new Http401UnauthorizedEntryPoint();
  }

  /**
   * Password encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /* (non-Javadoc)
   * @see org.springframework.security.config.annotation
   * .web.configuration.WebSecurityConfigurerAdapter
   * #configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
    	.antMatchers(HttpMethod.OPTIONS, "/**")
    	.antMatchers("/app/**/*.{js,html}")
        .antMatchers("/bower_components/**")
        .antMatchers("/i18n/**")
        .antMatchers("/content/**")
        .antMatchers("/test/**");
  }

  /* (non-Javadoc)
   * @see org.springframework.security.config.
   * annotation.web.configuration.WebSecurityConfigurerAdapter
   * #configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
        .authenticationEntryPoint(http401UnauthorizedEntryPoint()).and().csrf().disable().headers()
        .frameOptions().disable().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
        .antMatchers("/admin/login").permitAll()
        .antMatchers("/user/login").permitAll()
        .antMatchers("/user/register").permitAll()
        .antMatchers("/**").authenticated()
        .and()
        .apply(securityConfigurerAdapter());

  }

  /**
   * Security configurer adapter.
   *
   * @return the jwt configurer
   */
  private JwtConfigurer securityConfigurerAdapter() {
    return new JwtConfigurer(tokenProvider);
  }
}
