package com.wallet.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wallet.security.utils.JwtTokenUtil;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile({"dev", "prod"})
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.wallet.controller")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(securitySchemes()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Wallet API")
				.description("Wallet API - Documentação de acesso aos endpoints.")
				.version("1.0").build();
	}

	private ApiKey securitySchemes() {

		return new ApiKey("Authorization", "Authorization", "header");
	}
}
