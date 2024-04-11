package com.tom.passwordSafetyBox.security;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter (AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("attemptAuthentification");
		String email, password;
		try {
			Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
			email = requestMap.get("email");
			password = requestMap.get("password");
			System.out.println(email);
			System.out.println(password);
		} catch (IOException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
		return authenticationManager.authenticate(authenticationToken);
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,FilterChain chain, Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentification");
		User user = (User) authResult.getPrincipal();
		Algorithm algorithm =  Algorithm.HMAC256("MySecretKey");
		String jwtAccessToken = JWT.create().withSubject(user.getUsername())
											.withExpiresAt(new Date(System.currentTimeMillis()+15*60*1000))
											.withIssuer(request.getRequestURL().toString())
											.withClaim("roles",user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
											.sign(algorithm);
		
		String jwtRefreshToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		
		Map<String,String> idToken = new HashMap<>();
		idToken.put("accessToken", jwtAccessToken);
		idToken.put("refreshToken",jwtRefreshToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(),idToken);
		
	}

}
