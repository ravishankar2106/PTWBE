package com.bind.ptw.be.initializers;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Predicate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {            

	@Bean
	public Docket api() { 
		TypeResolver typeResolver = new TypeResolver();
        final ResolvedType jsonNodeType =
                typeResolver.resolve(
                        JsonNode.class);
        final ResolvedType stringType =
                typeResolver.resolve(
                        String.class);
		return new Docket(DocumentationType.SWAGGER_2)  
				.select()                                  
				.apis(RequestHandlerSelectors.any())              
				.paths(apiPaths())   
				.build().apiInfo(apiInfo()).alternateTypeRules(
                        new AlternateTypeRule(
                                jsonNodeType,
                                stringType))
				.securitySchemes(newArrayList(jwtTokenKey()))
				.securityContexts(newArrayList(securityContext()));   
	}

	public ApiInfo apiInfo() {
		return new ApiInfo("Auth Swagger", "", "1.0", "", new Contact("", "", ""), "Mangozteen Auth", "");
	}   

	private ApiKey jwtTokenKey() {
		return new ApiKey("Authorization", "Bearer ", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(securityPaths())
				.build();
	}

	private Predicate<String> apiPaths() {
		return regex("/api/v1.*");
	}

	private Predicate<String> securityPaths() {
		return and(
				regex("/.*"),
				not(regex("/userauth.*"))
				);
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope("ROLE_ADMIN", "System administrator");
		authorizationScopes[1] = new AuthorizationScope("ROLE_USER", "Tenant administrator");
		return newArrayList(
				new SecurityReference("Authorization", authorizationScopes));
	}
}