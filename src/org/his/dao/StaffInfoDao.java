package org.his.dao;

import javax.persistence.EntityManager;

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

}
