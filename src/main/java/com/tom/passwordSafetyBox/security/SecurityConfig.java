package com.tom.passwordSafetyBox.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tom.passwordSafetyBox.Service.AppRoleService;
import com.tom.passwordSafetyBox.Service.UserService;
import com.tom.passwordSafetyBox.entity.AppUser;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppUserDetailService appUserDetailService;
	
	@Autowired
	private AuthenticationConfiguration authConfiguration;
	
	
	//Cette méthode permet d’indiquer à Spring Security d’utiliser la classe AppUserDetailService pour authentifier des utilisateurs. 
	 @Bean
	 public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
	 	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	 authenticationManagerBuilder.userDetailsService(appUserDetailService).passwordEncoder(passwordEncoder);
	 	return authenticationManagerBuilder.build();
	 	
	 }
				
	
	 @Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.GET, "/safetybox/users/{email}").hasAnyAuthority("ADMIN", "USER"))
		.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST,"/safetybox/users/**").hasAuthority("ADMIN"))
		.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.GET,"/safetybox/users/**").hasAuthority("ADMIN"))
		.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.DELETE,"/safetybox/users/**").hasAuthority("ADMIN"))
		.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST,"/safetybox/addRoleToUser").hasAuthority("ADMIN"))
		.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
		.csrf(csrf->csrf.disable())
		.sessionManagement(session->session.sessionCreationPolicy( SessionCreationPolicy.STATELESS))
		.headers(frameOptions->frameOptions.disable())
		.addFilter(new JwtAuthenticationFilter(authenticationManager(authConfiguration)))
		.addFilterBefore(new JwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
	return http.build();
	 }


	 
	
	
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
	    return authConfiguration.getAuthenticationManager();}

}
