package com.itbulls.learnit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.persistence.repositories.springdata.UserJpaRepository;

@Service
public class UserService {

	@Autowired
	private UserJpaRepository<User> userRepository;

	@Transactional
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Transactional
	public List<User> findByFirstName(String name) {
		return userRepository.findByFirstName(name);
	}
	
	@Transactional
	public List<User> findByFirstNameCaseInsensitive(String name) {
		return userRepository.getByFirstNameCaseInsensitive(name);
	}

	@Transactional
	public User getById(Long id) {
		return userRepository.findById(id).orElseGet(null);
	}

	@Transactional
	public void deleteUser(Long personId) {
		userRepository.deleteById(personId);
	}

	@Transactional
	public boolean addUser(User person) {
		return userRepository.save(person) != null;
	}

	@Transactional
	public boolean updateUser(User person) {
		return userRepository.save(person) != null;
	}

	public List<User> getAllUsersOrderedByFirstName() {
		return userRepository.getAllUsersOrderByFirstName();
	}
}
