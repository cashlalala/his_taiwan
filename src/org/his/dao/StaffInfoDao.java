package org.his.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.his.JPAUtil;
import org.his.model.StaffInfo;

public class StaffInfoDao {

	public StaffInfoDao() {
		// TODO Auto-generated constructor stub
	}

	private EntityManager em = JPAUtil.getEntityManager();

	public void persist(StaffInfo staffInfo) {
		em.persist(staffInfo);
	}

	public StaffInfo QueryStaffInfoById(int id) {
		return em.find(StaffInfo.class, id);
	}

	public StaffInfo QueryIfUserIsValid(String id, String pwd) {
		Query qry = em.createNamedQuery("QueryIfUserIsValid", StaffInfo.class);
		qry.setParameter("sId", id);
		qry.setParameter("pwd", pwd);
		StaffInfo sInfo = null;
		try {
			sInfo = (StaffInfo) qry.getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			ex.printStackTrace();
		}
		return sInfo;
	}

}
