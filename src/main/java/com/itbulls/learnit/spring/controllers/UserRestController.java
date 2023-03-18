package com.itbulls.learnit.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable Long id) {
		return userService.getById(id);
	}

	@RequestMapping(value = "/usersByFirstName/{name}", method = RequestMethod.GET)
	public List<User> getUsersByFirstName(@PathVariable String name) {
		return userService.findByFirstName(name);
	}
	
	@RequestMapping(value = "/usersByFirstNameCaseInsensitive/{name}", method = RequestMethod.GET)
	public List<User> getUserByFirstNameCaseInsensitive(@PathVariable String name) {
		return userService.findByFirstNameCaseInsensitive(name);
	}
	
	@RequestMapping(value = "/usersOrderedByFirstName", method = RequestMethod.GET)
	public List<User> getUsersOrderedByFirstName() {
		return userService.getAllUsersOrderedByFirstName();
	}
	
	

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> getAll() {
		return userService.getAllUsers();
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public HttpStatus deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return HttpStatus.NO_CONTENT;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public HttpStatus insertUser(@RequestBody User user) {
		return userService.addUser(user) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public HttpStatus updateUser(@RequestBody User user) {
		return userService.updateUser(user) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
	}
}