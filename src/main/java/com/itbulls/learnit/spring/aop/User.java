package com.itbulls.learnit.spring.aop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("user")
public class User {
	
	@Value("John")
	private String firstName;
	
	@Value("Doe")
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void throwException() {
		System.out.println("Exception is thrown");
		throw new RuntimeException();
	}
}
