package com.itbulls.learnit.spring.persistence.repositories;

import com.itbulls.learnit.spring.persistence.entities.Privilege;

public interface PrivilegeRepository {

	Privilege findByName(String privilegeName);

	void save(Privilege privilege);

}
