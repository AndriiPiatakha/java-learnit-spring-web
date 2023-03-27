package com.itbulls.learnit.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.persistence.repositories.springjdbc.UserDao;

@Service
public class UserServiceJdbcDao {

	@Autowired
	private UserDao userDao;

	public Integer getAmountOfUsersInSystem() {
		return userDao.getTotalUsersCount();
	}
	
	public Integer createTestUser() {
		return userDao.addSampleUser();
	}
	
	public String getLastNameOfuserById(Integer id) {
		return userDao.getLastNameOfUserById(id);
	}
	
	public String getLastNameOfUserByBean() {
		User user = new User();
		user.setId(7L);
		return userDao.getLastNameOfUserByUserBean(user);
	}
	
	public int[] batchInsertDemo() {
		List<User> users = new ArrayList<>();
		users.add(new User("John", "Smith"));
		users.add(new User("William", "Jackson"));
		
		return userDao.batchInsertDemo(users);
	}
	
	public User getUserById(Integer id) {
		return userDao.getUserById(id);
	}
	
	public Integer createTestUser2() {
		return userDao.addSampleUserWithSimpleJdbcInsertDemo();
	}

	public User getUserWithFirstLastNameById(Integer id) {
		return userDao.getUserWithFirstLastNameByIdFromStoredProcedure(id);
	}
	
}
