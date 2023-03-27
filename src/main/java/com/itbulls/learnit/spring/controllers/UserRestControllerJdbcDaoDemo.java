package com.itbulls.learnit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.service.UserServiceJdbcDao;

@RestController
public class UserRestControllerJdbcDaoDemo {
	
	@Autowired
	private UserServiceJdbcDao userService;
	
	@RequestMapping(value = "/user/count", method = RequestMethod.GET)
	public Integer getUserById() {
		return userService.getAmountOfUsersInSystem();
	}
	
	@RequestMapping(value = "/user/add-test-user", method = RequestMethod.GET)
	public Integer createSampleUser() {
		return userService.createTestUser();
	}
	
	@RequestMapping(value = "/user/lastName/{id}", method = RequestMethod.GET)
	public String getLastNameOfUserById(@PathVariable Integer id) {
		return userService.getLastNameOfuserById(id);
	}
	
	@RequestMapping(value = "/user/lastName-bean-demo", method = RequestMethod.GET)
	public String getLastNameOfUserByBean() {
		return userService.getLastNameOfUserByBean();
	}
	
	@RequestMapping(value = "/user/batch-insert-demo", method = RequestMethod.GET)
	public int[] batchInsertDemo() {
		return userService.batchInsertDemo();
	}
	
	@RequestMapping(value = "/user/jdbc-demo/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable Integer id) {
		return userService.getUserById(id);
	}
	
	@RequestMapping(value = "/user/add-test-user-2", method = RequestMethod.GET)
	public Integer createSampleUser2() {
		return userService.createTestUser2();
	}
	
	@RequestMapping(value = "/user/lastName/procedure-demo/{id}", method = RequestMethod.GET)
	public User getUserWithFirstLastNameById(@PathVariable Integer id) {
		return userService.getUserWithFirstLastNameById(id);
	}

}
