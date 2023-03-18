package com.itbulls.learnit.spring.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.itbulls.learnit.spring.persistence.entities.Privilege;
import com.itbulls.learnit.spring.persistence.entities.Role;
import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.persistence.repositories.PrivilegeRepository;
import com.itbulls.learnit.spring.persistence.repositories.RoleRepository;
import com.itbulls.learnit.spring.persistence.repositories.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	public static final String DELETE_PRIVILEGE = "DELETE_PRIVILEGE";
	public static final String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
	public static final String READ_PRIVILEGE = "READ_PRIVILEGE";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_MANAGER = "ROLE_MANAGER";

	private boolean isAlreadyConfigured;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (isAlreadyConfigured) {
			return;
		}

		Privilege readPrivilege = createPrivilegeIfNotFound(READ_PRIVILEGE);
		Privilege writePrivilege = createPrivilegeIfNotFound(WRITE_PRIVILEGE);
		Privilege deletePrivilege = createPrivilegeIfNotFound(DELETE_PRIVILEGE);

		List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, deletePrivilege);
		List<Privilege> managerPrivileges = Arrays.asList(readPrivilege, writePrivilege);
		
		createRoleIfNotFound(ROLE_ADMIN, adminPrivileges);
		createRoleIfNotFound(ROLE_MANAGER, managerPrivileges);
		createRoleIfNotFound(ROLE_USER, Arrays.asList(readPrivilege));

		Role adminRole = roleRepository.findByName(ROLE_ADMIN);
		Role managerRole = roleRepository.findByName(ROLE_MANAGER);
		
		createUserIfNotFound("admin@test.com", adminRole, "admin");
		createUserIfNotFound("manager@test.com", managerRole, "manager");

		isAlreadyConfigured = true;
	}

	@Transactional
	private void createUserIfNotFound(String email, Role role, String password) {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			user = new User();
			user.setFirstName("Admin");
			user.setLastName("Admin");
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(email);
			user.setRoles(new HashSet(Arrays.asList(role)));
			user.setEnabled(true);
			userRepository.save(user);
		}
	}

	@Transactional
	private Privilege createPrivilegeIfNotFound(String name) {

		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(new HashSet(privileges));
			roleRepository.save(role);
		}
		return role;
	}
}
