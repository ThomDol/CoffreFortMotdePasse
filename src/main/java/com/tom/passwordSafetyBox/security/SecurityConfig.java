package com.tom.passwordSafetyBox.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import com.tom.passwordSafetyBox.Service.UserService;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;

	@Autowired
	private AppUserDetailService appUserDetailService;

	@Autowired
	private AuthenticationConfiguration authConfiguration;

	@Autowired
	private CorsFilter corsFilter;


	//Cette méthode permet d’indiquer à Spring Security d’utiliser la classe AppUserDetailService pour authentifier des utilisateurs.
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(appUserDetailService).passwordEncoder(passwordEncoder);
		return authenticationManagerBuilder.build();

	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(corsFilter, CorsFilter.class)
	//	http.cors(cors->cors.configurationSource(request->new CorsConfiguration().applyPermitDefaultValues()))
				.csrf(csrf->csrf.disable())
				.sessionManagement(session->session.sessionCreationPolicy( SessionCreationPolicy.STATELESS))
				.headers(frameOptions->frameOptions.disable())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST,"/safetybox/addUsers").permitAll())
				.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.DELETE,"/safetybox/users/**").hasAuthority("ADMIN"))
				.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.GET,"/safetybox/users").hasAuthority("ADMIN"))
				.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
				.addFilter(new JwtAuthenticationFilter(authenticationManager(authConfiguration)))

				.addFilterBefore(new JwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}



	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		return authConfiguration.getAuthenticationManager();}

}
