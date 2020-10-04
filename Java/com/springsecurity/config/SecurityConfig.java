package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.inMemoryAuthentication()
         .withUser("S0020216140").password(passwordEncoder().encode("Pery/155712134")).roles("CONSULTANT")
         .and()
         .withUser("S0021312329").password(passwordEncoder().encode("BK6NB=20")).roles("HRUSER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/views/**").hasRole("CONSULTANT")
			.antMatchers("/").hasRole("HRUSER")
			.antMatchers("/login*").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.and()
	        .httpBasic();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	} 

}
