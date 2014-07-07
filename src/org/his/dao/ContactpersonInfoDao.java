package org.his.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.his.JPAUtil;
import org.his.model.ContactpersonInfo;

public class ContactpersonInfoDao {

	public ContactpersonInfoDao() {
	}
	
	private EntityManager em = JPAUtil.getEntityManager();
	
	public ContactpersonInfo merge(ContactpersonInfo contactpersonInfo) {
		try {
			return em.merge(contactpersonInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void persist(ContactpersonInfo contactpersonInfo) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(contactpersonInfo);
		tx.commit();
	}
	
	public void close(){
		em.close();
	}
	
	public ContactpersonInfo QueryContactInfoById(String pGuid) {
		return em.find(ContactpersonInfo.class, pGuid);
	}
	

}
