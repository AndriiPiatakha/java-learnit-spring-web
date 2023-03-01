package com.itbulls.learnit.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("===== In Authentication Provider method =====");
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		System.out.println("Authentication object: " + authentication);
		System.out.println("User Name: " + username);
		System.out.println("Password: " + password);
		System.out.println("authentication.getDetails(): " + authentication.getDetails());
		System.out.println("authentication.getPrincipal(): " + authentication.getPrincipal());
		System.out.println("authentication.getAuthorieis(): " + authentication.getAuthorities());

		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (user != null) {
			if (isPasswordValid(user, password)) {
				return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
			} else {
				throw new BadCredentialsException("Incorrect login/password pair");
			}
		} else {
			return null;
		}
	}

	private boolean isPasswordValid(UserDetails user, String password) {
		return passwordEncoder.matches(password, user.getPassword());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("===== in supports() method =====");
		System.out.println("Authentication class: " + authentication);
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
