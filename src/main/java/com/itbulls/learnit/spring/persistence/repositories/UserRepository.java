package com.itbulls.learnit.spring.persistence.repositories;

import com.itbulls.learnit.spring.persistence.entities.User;

public interface UserRepository {

	void save(User user);

	User findByEmail(String email);

}
