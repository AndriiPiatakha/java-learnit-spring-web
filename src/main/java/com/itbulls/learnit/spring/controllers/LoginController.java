package com.itbulls.learnit.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class LoginController {

	@RequestMapping("/login_page")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/login_remember_me")
	public String loginRememberMe() {
		return "login_remember_me";
	}
	
}
