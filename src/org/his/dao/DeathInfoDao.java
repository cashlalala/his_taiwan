package org.his.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.his.JPAUtil;
import org.his.model.DeathInfo;

public class DeathInfoDao {

	public DeathInfoDao() {
	}

	private EntityManager em = JPAUtil.getEntityManagerFactory()
			.createEntityManager();
	
	public DeathInfo QueryDeathInfoById(String guid) {
		return em.find(DeathInfo.class, guid);
	}

	public void persist(DeathInfo deathInfo) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.persist(deathInfo);
			etx.commit();			
		}
		catch (Exception e) {
			e.printStackTrace();
			etx.rollback();	
		}
	}
	
	public void merge(DeathInfo deathInfo) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.merge(deathInfo);
			etx.commit();			
		}
		catch (Exception e) {
			e.printStackTrace();
			etx.rollback();	
		}
	}

	public void remove(DeathInfo deathInfo) {
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		em.merge(deathInfo);
		em.remove(deathInfo);
		etx.commit();		
	}
}
