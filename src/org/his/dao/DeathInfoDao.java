package org.his.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.his.JPAUtil;
import org.his.model.DeathInfo;

public class DeathInfoDao {

	public DeathInfoDao() {
	}

	private EntityManager em = JPAUtil.getEntityManager();

	public DeathInfo QueryDeathInfoById(String guid) {
		try {
			return em.find(DeathInfo.class, guid);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void persist(DeathInfo deathInfo) {
		em.persist(deathInfo);
	}

	public DeathInfo merge(DeathInfo deathInfo) {
		try {
			return em.merge(deathInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void remove(DeathInfo deathInfo) {
		em.remove(deathInfo);
	}
}
