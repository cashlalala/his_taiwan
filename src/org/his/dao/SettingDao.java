package org.his.dao;

import javax.persistence.EntityManager;

import org.his.JPAUtil;
import org.his.model.Setting;

public class SettingDao {

	private EntityManager em = JPAUtil.getEntityManager();

	public SettingDao() {
		// TODO Auto-generated constructor stub
	}

	public Setting QuerySettingById(int id) {
		return em.find(Setting.class, id);
	}

}
