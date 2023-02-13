package com.itbulls.learnit.spring.persistence.repositories;

import com.itbulls.learnit.spring.persistence.entities.Role;

public interface RoleRepository {

	Role findByName(String roleName);

	void save(Role role);

}
