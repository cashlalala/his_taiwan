package org.his.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.his.JPAUtil;
import org.his.model.ContactpersonInfo;

public class ContactpersonInfoDao {

	public ContactpersonInfoDao() {
		// TODO Auto-generated constructor stub
	}
	
	private EntityManager em = JPAUtil.getEntityManagerFactory()
			.createEntityManager();
	
	public void persist(ContactpersonInfo contactpersonInfo) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(contactpersonInfo);
		tx.commit();
	}
	
	public ContactpersonInfo QueryContactInfoById(String pGuid) {
		return em.find(ContactpersonInfo.class, pGuid);
	}
	

}
