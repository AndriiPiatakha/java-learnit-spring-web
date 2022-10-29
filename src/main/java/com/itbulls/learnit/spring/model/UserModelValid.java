package com.itbulls.learnit.spring.model;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component("user-valid")
public class UserModelValid {

	@NotEmpty(message = "First Name should not be empty")
	@Size(min = 3, max = 20, message = "First Name should be between 3 and 20 characters" )
	private String firstName;
	
	@NotEmpty(message = "Last Name should not be empty")
	@Size(min = 3, max = 20, message = "Last Name should be between 3 and 20 characters" )
	private String lastName;
	
	@NotEmpty(message = "Email should not be empty")
	@Email(message = "Please, use real email")
	private String email;
	
	@NotEmpty(message = "Password should not be empty")
	@Size(min = 3, max = 20, message = "Password should be between 3 and 20 characters" )
	private String password;
	
//	@Valid
//	private Role role;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + "]";
	}
}
