package org.his.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.his.JPAUtil;
import org.his.model.ContactpersonInfo;
import org.his.model.HlsGroup;

public class HlsGroupDao {

	public HlsGroupDao() {
		// TODO Auto-generated constructor stub
	}

	private EntityManager em = JPAUtil.getEntityManagerFactory()
			.createEntityManager();

	public void persist(ContactpersonInfo contactpersonInfo) {
		em.persist(contactpersonInfo);
	}

	public String getComposedString(HlsGroup hlsGrp) {
		return String.format("%s - %s", hlsGrp.getValue(),
				hlsGrp.getDescrition());
	}

	@SuppressWarnings("unchecked")
	public List<HlsGroup> QueryHlsGroups() {
		Query query = em.createNamedQuery("QueryHlsgroups", HlsGroup.class);
		return (List<HlsGroup>) query.getResultList();
	}

	public String QueryCompStringByValue(String value) {
		Query query = em.createNamedQuery("QueryHlsgroupByValue", HlsGroup.class);
		query.setParameter("val", value);
		try {
			HlsGroup hlsGrp = (HlsGroup) query.getSingleResult();
			return getComposedString(hlsGrp);
		}
		catch (Exception e) {
//			NoResultException e
			e.printStackTrace();
			return null;
		}
	}

}
