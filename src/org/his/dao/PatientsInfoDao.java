package org.his.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.his.JPAUtil;
import org.his.model.PatientsInfo;

public class PatientsInfoDao {

	private EntityManager em = JPAUtil.getEntityManagerFactory()
			.createEntityManager();

	public PatientsInfoDao() {
	}

	public void persist(PatientsInfo patiensInfo) {
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		em.merge(patiensInfo);
		etx.commit();
	}

	public void merge(PatientsInfo patiensInfo) {
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		em.merge(patiensInfo);
		etx.commit();
	}
	
	public void DeleteAutoGenUser(String id) {
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Query query = em.createNamedQuery("DeleteAutoGenUser");
		query.setParameter("uuid", id);
		@SuppressWarnings("unused")
		int affectedItem = query.executeUpdate();
		etx.commit();
	}
	
	public void remove(PatientsInfo patiensInfo) {
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		em.merge(patiensInfo);
		em.remove(patiensInfo);
		etx.commit();
	}
	
	public PatientsInfo QueryPatientInfoById(String id) {
		return em.find(PatientsInfo.class, id);
	}

	public int getNewPNo() {
		Query query = em
				.createNativeQuery("SELECT IF(COUNT(p_no) <> 0,MAX(p_no)+1,0) FROM patients_info");
		int pNo = ((BigInteger) query.getSingleResult()).intValue();
		return pNo;
	}
	
	public List<PatientsInfo> getExistedPatients() {
		return getExistedPatients(null, 0, 0);
	}

	public int getPatientCount() {
		return ((Long) em.createNamedQuery("QueryPatientCount")
				.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<PatientsInfo> getExistedPatients(String condition, int offset,
			int perpage) {
		String sql = "SELECT * FROM patients_info WHERE exist = 1 ";
		if (condition != null) {
			sql = String.format("%s AND %s", sql, condition);
		}
		Query query = em.createNativeQuery(sql, PatientsInfo.class);
		query.setFirstResult(offset);
		if (perpage != 0) {
			query.setMaxResults(perpage);
		}
		return query.getResultList();
	}

}
