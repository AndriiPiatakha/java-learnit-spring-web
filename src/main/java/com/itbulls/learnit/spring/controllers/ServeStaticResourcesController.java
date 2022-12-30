package com.itbulls.learnit.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class ServeStaticResourcesController {
	
	
	@RequestMapping("/serve-static")
	public String serveStaticResourcesDemo() {
		return "staticResourcesDemo";
	}

}
