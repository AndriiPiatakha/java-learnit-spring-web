package com.itbulls.learnit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class PropertiesDemoController {

	@Value("${test.string.value}")
	private String testStringProperty;
	
	@Value("${test.integer.value}")
	private Integer testIntegerProperty;
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/props-demo")
	public String propertiesDemo() {
		System.out.println("Test String property: " + testStringProperty);
		System.out.println("Test Integer property: " + testIntegerProperty);
		System.out.println("Test String property, extracted from Environment: " + env.getProperty("test.string.value"));
		
		return "props-demo";
	}
	
}
