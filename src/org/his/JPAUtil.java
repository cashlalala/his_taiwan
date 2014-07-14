package org.his;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory entityManagerFactory;

	private static EntityManager entityManager;

	private static Properties properties;

	public static final String PU_NAME = "hospital";

	static {
		properties = new Properties();
	}

	public static void setUrl(String url) {
		properties.put("javax.persistence.jdbc.url", url);
	}

	public static void setPassword(String pwd) {
		properties.put("javax.persistence.jdbc.password", pwd);
	}

	public static void setUser(String usr) {
		properties.put("javax.persistence.jdbc.user", usr);
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(
					PU_NAME, properties);
		}
		return entityManagerFactory;
	}

	public static EntityManagerFactory getEntityManagerFactory(String puName) {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory(puName);
		}
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = getEntityManagerFactory().createEntityManager();
		}
		return entityManager;
	}

	public static EntityTransaction getTransaction() {
		return entityManager.getTransaction();
	}

	public static void shutdown() {
		getEntityManager().close();
		getEntityManagerFactory().close();
	}
}
