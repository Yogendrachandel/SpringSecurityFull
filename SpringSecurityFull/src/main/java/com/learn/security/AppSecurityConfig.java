package com.learn.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.learn.userpermission.ApplicationUserPermission;
import com.learn.userrole.ApplicationUserRole;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//the spring username is user and password  will be created by spring in console with base64
	//The important thing is Order of rule is very important-check this link-  https://stackoverflow.com/questions/30819337/multiple-antmatchers-in-spring-security
	//more specific rule should be written first.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("## Inside the configure(HttpSecurity http)  method ##");
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/","index","/css/*","/js/*") //to give permission like  root  and index and css and js without security
			.permitAll()
			.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())// only user who has Student role access this resources.
			.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermissions())
			.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermissions())
			.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermissions())
			.antMatchers("/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMIN_TRANIEE.name())//This is for GET endpoint - we are allowing ADMIN and ADMIN_TRANIEE only not STUDENT.
			.anyRequest().authenticated()
			.and()
			.formLogin().
			 and()
			.httpBasic();
	}
	
	
	
	/*
	 * This is spring InMemoryUserDetailsManager implemenation.in which we have
	 * created 3 user by using Spring User class.
	 */
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		System.out.println("## Inside the userDetailsService method ##");

		/* ##########This was Role-based AUthentication.###############
		
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
		
		
		//Create one user with ADMIN_TRANIEE role with 4 permissions.
				UserDetails tomuser= User.builder().username("tom")
						.password(createOurpasswordEncoder().encode("1234"))// we are specifing the ecncder otherwise getting error
		 				.roles(ApplicationUserRole.ADMIN_TRANIEE.name()) // spring security internally convert this role as ROLE_ADMIN_TRANIEE and add in GrantedAuthority .open this method and explore.
		 				.build();
		 				
		 				
		 				
		 */		
		

		/* ############# This is Authority(permission) based Authentication############## */
		
		//Create one user with STUDENT role with 0 permissions.
		UserDetails amanUser= User.builder().username("annasmith")
		 				.password(createOurpasswordEncoder().encode("1234"))// we are specifing the ecncder otherwise getting error
		 				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities()) // spring security internally convert this role as ROLE_STUDENT and add in GrantedAuthority .open this method and explore.
		 				.build();
		
		//Create one user with ADMIN role with 4 permissions.
		UserDetails ajayuser= User.builder().username("linda")
				.password(createOurpasswordEncoder().encode("1234"))// we are specifing the ecncder otherwise getting error
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities()) // spring security internally convert this role as ROLE_ADMIN and add in GrantedAuthority .open this method and explore.
 				.build();
		
		
		//Create one user with ADMIN_TRANIEE role with 4 permissions.
		UserDetails tomuser= User.builder().username("tom")
				.password(createOurpasswordEncoder().encode("1234"))// we are specifing the ecncder otherwise getting error
				.authorities(ApplicationUserRole.ADMIN_TRANIEE.getGrantedAuthorities()) // spring security internally convert this role as ROLE_ADMIN_TRANIEE and add in GrantedAuthority .open this method and explore.
 				.build();
		 						
		
		List<UserDetails> userLists= new ArrayList<>();
		
		userLists.add(amanUser);
		userLists.add(ajayuser);
		userLists.add(tomuser);
		
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(userLists);// in production not correct way.
		
		return inMemoryUserDetailsManager;
	}
	
	
	//PasswordEncoder is Interface and BCryptPasswordEncoder is the implemented class
	@Bean
	PasswordEncoder createOurpasswordEncoder() {
		
		return new BCryptPasswordEncoder(10);
	}
	
	
	

}
