package com.itbulls.learnit.spring.persistence.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.persistence.repositories.UserRepository;

@Repository
public class DefaultUserRepository implements UserRepository {

	@Override
	public void save(User user) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(user);
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

	@Override
	public User findByEmail(String email) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			query.setParameter("email", email);
			User user = query.getResultList().stream().findFirst().orElse(null);
			em.getTransaction().commit();
			return user;
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
