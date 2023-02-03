package com.itbulls.learnit.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class FakeControllersForSpringSecurityDemo {

	
	@RequestMapping("/anonymous")
	public String anonymous() {
		return "anonymous";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@RequestMapping("/homepage")
	public String homepage() {
		return "homepage";
	}
}
