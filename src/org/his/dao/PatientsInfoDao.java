package org.his.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.his.JPAUtil;
import org.his.model.PatientsInfo;

public class PatientsInfoDao {

	private EntityManager em = JPAUtil.getEntityManager();

	public PatientsInfoDao() {
	}

	public void persist(PatientsInfo patiensInfo) {
		em.persist(patiensInfo);
	}

	public PatientsInfo merge(PatientsInfo patiensInfo) {
		try {
			return em.merge(patiensInfo);	
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void close() {
		em.close();
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
		em.remove(patiensInfo);
		etx.commit();
	}
	
	public PatientsInfo QueryPatientInfoById(String id) {
		return em.find(PatientsInfo.class, id);
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
	
	@SuppressWarnings("unchecked")
	public List<PatientsInfo> getPatientsBySearch(String target){
		try{
			String sql = "SELECT * FROM patients_info " +
					"WHERE exist =  1 " +
					"AND ( UPPER(p_no) LIKE UPPER ('%" + target.replace(" ", "%") + "%') " +
					"OR UPPER(nhis_no) LIKE UPPER('%" + target.replace(" ", "%") + "%') " +
	                "OR UPPER(nia_no) LIKE UPPER('%" + target.replace(" ", "%") + "%') " +
	                "OR UPPER(concat(firstname,' ',lastname)) LIKE UPPER('%" + target.replace(" ", "%") + "%')"+
	                ")";
			Query query = em.createNativeQuery(sql, PatientsInfo.class);
			return query.getResultList();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
