package com.itbulls.learnit.spring.persistence.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.itbulls.learnit.spring.persistence.entities.Privilege;
import com.itbulls.learnit.spring.persistence.repositories.PrivilegeRepository;

@Repository
public class DefaultPrivilegeRepository implements PrivilegeRepository {

	@Override
	public Privilege findByName(String privilegeName) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Privilege> query = em.createQuery("SELECT p FROM Privilege p WHERE p.name = :privilegeName", Privilege.class);
			query.setParameter("privilegeName", privilegeName);
			Privilege privilege = query.getResultList().stream().findFirst().orElse(null);
			em.getTransaction().commit();
			return privilege;
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
	public void save(Privilege privilege) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("persistence-unit");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(privilege);
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
