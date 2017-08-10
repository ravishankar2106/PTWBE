package com.bind.ptw.be.initializers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bind.ptw.be.util.CorsHelper;



// TODO: Auto-generated Javadoc
/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@Import(SwaggerConfig.class)
public class WebConfigurer extends WebMvcConfigurerAdapter implements ServletContextInitializer,
EmbeddedServletContainerCustomizer {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	@Autowired
	CorsHelper corsHelper;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Cors filter.
	 *
	 * @return the cors filter
	 */
	@Bean
	public CorsFilter corsFilter() {
		log.debug("Registering CORS filter");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(corsHelper.allowedOrigins());
		config.setAllowedHeaders(corsHelper.allowedHeaders());
		config.setAllowedMethods(corsHelper.allowedMethods());
		config.setMaxAge(1800L);
		source.registerCorsConfiguration("/api/**", config);
		source.registerCorsConfiguration("/v2/api-docs", config);
		source.registerCorsConfiguration("/oauth/**", config);
		return new CorsFilter(source);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub

	}
}
