package com.itbulls.learnit.spring.errors;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itbulls.learnit.spring.model.UserModel;

@Controller
@RequestMapping("/test")
public class ExceptionHandlerController {
	
	@RequestMapping(value = "/user/{email}")
	public String getUser(@PathVariable("email") String email, 
			Model model) throws Exception {
		if (email.equals("first.user@email.com")) {
			throw new UserNotFoundException(email);
		} else if (email.equals("SQL")) {
			throw new SQLException("SQLException, email = " + email);
		} else if (email.equals("IO")) {
			throw new IOException("IOException, email = " + email);
		} else if (email.equals("john.doe@email.com")) {
			UserModel user = new UserModel();
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setEmail("john.doe@email.com");
			model.addAttribute("user", user);
			return "printUser";
		} else {
			throw new Exception("Generic Exception, id = " + email);
		}

	}

	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(HttpServletRequest request, 
			Exception ex, Model model) {
		model.addAttribute("exception", ex);
		model.addAttribute("url", request.getRequestURL());
		return "error";
	}
	
}
