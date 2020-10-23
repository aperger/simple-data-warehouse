package com.adverity.demo.simpledatawarehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				.antMatchers(HttpMethod.POST, "/**").denyAll()
				.antMatchers(HttpMethod.POST, "/**").denyAll()
				.antMatchers(HttpMethod.PATCH, "/**").denyAll()
				.antMatchers(HttpMethod.DELETE, "/**").denyAll();
	}
}
