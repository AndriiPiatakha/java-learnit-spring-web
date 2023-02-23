package com.itbulls.learnit.spring.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/manager")
	public String manager() {
		return "manager";
	}
	
	@RequestMapping("/homepage")
	public String homepage() {
		return "homepage";
	}
	
	@RequestMapping("/delete-order")
	@ResponseBody
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
//	@PostAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
//	@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
	public String deleteOrder() {
		System.out.println("///// In the deleteOrder");
		return "order is deleted";
	}
	
}
