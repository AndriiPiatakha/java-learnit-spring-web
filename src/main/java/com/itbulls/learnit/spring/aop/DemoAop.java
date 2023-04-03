package com.itbulls.learnit.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class DemoAop {
	public static void main(String[] args) {
		var context = new AnnotationConfigApplicationContext();
		context.scan("com.itbulls.learnit.spring.aop");
		context.refresh();

		User user = (User) context.getBean("user");
		
		System.out.println("===== First Method Demo =====");
		System.out.println(user.getFirstName());
		
		System.out.println("===== Second Method Demo =====");
		user.throwException();
	}
}
