package com.learn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learn.auth.ApplicationUserService;
import com.learn.userrole.ApplicationUserRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //only when method level  @PreAuthorize annotation used.
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private  PasswordEncoder passwordEncoder;
	
	@Autowired
	private  ApplicationUserService applicationUserService;
	
	
	
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
		//	.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermissions())
		//	.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermissions())
		//	.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermissions())
		//	.antMatchers("/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMIN_TRANIEE.name())//This is for GET endpoint - we are allowing ADMIN and ADMIN_TRANIEE only not STUDENT.
			.anyRequest().authenticated()
			.and()
			.formLogin().
			 and()
			.httpBasic();
	}
	
	
	
	//This overide method is used to perform database Authentication.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(daoAuthenticationProvider());
		
		
	}


	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
				//An AuthenticationProvider implementation that retrieves user details from a UserDetailsService.
				DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
				provider.setUserDetailsService(applicationUserService); //because applicationUserService implenets UserDetailsService
				provider.setPasswordEncoder(passwordEncoder);
				
				return provider;
	}






	
	
	
	

}
