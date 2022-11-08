package com.itbulls.learnit.spring.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found") // 404
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String email) {
		super("UserNotFoundException with email: " + email);
	}
}
