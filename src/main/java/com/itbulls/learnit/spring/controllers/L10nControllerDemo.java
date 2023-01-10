package com.itbulls.learnit.spring.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class L10nControllerDemo {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/lang-demo")
	public String langDemo() {
		return "langDemo";
	}
	
	@GetMapping("/message-source-demo")
	public String messageSourceDemo(HttpSession session) {
		session.setAttribute("testMsg", 
				messageSource.getMessage("random.text", null, LocaleContextHolder.getLocale()));
		System.out.println(messageSource.getMessage("random.text", null, LocaleContextHolder.getLocale()));
		return "messageSourceDemo";
	}

}
