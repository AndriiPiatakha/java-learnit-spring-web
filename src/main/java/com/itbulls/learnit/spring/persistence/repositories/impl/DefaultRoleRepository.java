package com.itbulls.learnit.spring.persistence.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.itbulls.learnit.spring.persistence.entities.Role;
import com.itbulls.learnit.spring.persistence.repositories.RoleRepository;

@Repository
public class DefaultRoleRepository implements RoleRepository {

	@Override
	public Role findByName(String roleName) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class);
			query.setParameter("roleName", roleName);
			Role role = query.getResultList().stream().findFirst().orElse(null);
			em.getTransaction().commit();
			return role;
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void save(Role role) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(role);
			em.getTransaction().commit();
		} finally {
			if (emf != null) {
				emf.close();
			}
			if (em != null) {
				em.close();
			}
		}
	}

}
