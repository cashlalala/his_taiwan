package org.his;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory entityManagerFactory;
	
	private static EntityManager entityManager;

	static {
		try {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("new_hospital");
			
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	public static EntityManager getEntityManager() {
		return entityManager;
	}
	
	public static EntityTransaction getTransaction() {
		return entityManager.getTransaction();
	}

	public static void shutdown() {
		entityManager.close();
		getEntityManagerFactory().close();
	}
}
