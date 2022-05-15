package com.learn.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.learn.userrole.ApplicationUserRole;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//the spring username is user and password  will be created by spring in console with base64
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*") //to give permission like  root  and index and css and js without security
			.permitAll()
			.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())// only user who has Student role access this resources.
			.anyRequest().authenticated()
			.and()
			.formLogin().
			 and()
			.httpBasic();
	}
	
	
	
	/*
	 * This is spring InMemoryUserDetailsManager implemenation.in which we have
	 * created two user by using Spring User class.
	 */
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		//Create one user with STUDENT role with 0 permissions.
		UserDetails amanUser= User.builder().username("annasmith")
		 				.password(createOurpasswordEncoder().encode("1234"))// we are specifing the ecncder otherwise getting error
		 				.roles(ApplicationUserRole.STUDENT.name()) // spring security internally convert this role as ROLE_STUDENT and add in GrantedAuthority .open this method and explore.
		 				.build();
		
		//Create one user with ADMIN role with 4 permissions.
		UserDetails ajayuser= User.builder().username("linda")
				.password(createOurpasswordEncoder().encode("1234"))// we are specifing the ecncder otherwise getting error
 				.roles(ApplicationUserRole.ADMIN.name()) // spring security internally convert this role as ROLE_ADMIN and add in GrantedAuthority .open this method and explore.
 				.build();
		
		List<UserDetails> userLists= new ArrayList<>();
		
		userLists.add(amanUser);
		userLists.add(ajayuser);
		
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(userLists);// in production not correct way.
		
		return inMemoryUserDetailsManager;
	}
	
	
	//PasswordEncoder is Interface and BCryptPasswordEncoder is the implemented class
	@Bean
	PasswordEncoder createOurpasswordEncoder() {
		
		return new BCryptPasswordEncoder(10);
	}
	
	
	

}
