package org.his.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.his.JPAUtil;
import org.his.model.ContactpersonInfo;
import org.his.model.PatientsInfo;

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
		em.persist(contactpersonInfo);
	}
	
	public void close(){
		em.close();
	}
	
	public ContactpersonInfo QueryContactInfoById(String pGuid) {
		return em.find(ContactpersonInfo.class, pGuid);
	}
	

}
