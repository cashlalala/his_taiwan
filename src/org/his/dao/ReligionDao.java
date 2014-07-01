package org.his.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.his.JPAUtil;
import org.his.model.ContactpersonInfo;
import org.his.model.Religion;

public class ReligionDao {

	public ReligionDao() {
		// TODO Auto-generated constructor stub
	}

	private EntityManager em = JPAUtil.getEntityManagerFactory()
			.createEntityManager();

	public void persist(ContactpersonInfo contactpersonInfo) {
		em.persist(contactpersonInfo);
	}

	public String getComposedString(Religion religion) {
		return String.format("%s - %s", religion.getValue(),
				religion.getDescrition());
	}

	@SuppressWarnings("unchecked")
	public List<Religion> QueryReligions() {
		Query query = em.createNamedQuery("QueryReligions", Religion.class);
		return (List<Religion>) query.getResultList();
	}

	public String QueryCompStringByValue(String value) {
		Query query = em.createNamedQuery("QueryReligionByValue", Religion.class);
		query.setParameter("val", value);
		try {
			Religion religion = (Religion) query.getSingleResult();
			return getComposedString(religion);
		}
		catch (Exception e) {
//			NoResultException e
			e.printStackTrace();
			return null;
		}
		
	}

}
