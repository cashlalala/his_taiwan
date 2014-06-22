package org.his.dao;

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
		em.persist(patiensInfo);
		etx.commit();
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
