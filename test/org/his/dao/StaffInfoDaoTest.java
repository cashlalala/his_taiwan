package org.his.dao;

import org.his.JPAUtil;
import org.his.model.StaffInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cc.johnwu.sql.HISPassword;

public class StaffInfoDaoTest {

	private static StaffInfoDao staffInfoDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JPAUtil.getEntityManagerFactory("UT_hospital");
		staffInfoDao = new StaffInfoDao();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPersist() {
	}

	@Test
	public void testQueryStaffInfoById() {
		StaffInfo sinfo = staffInfoDao.QueryStaffInfoById(1);
		Assert.assertNotNull(sinfo);
	}
	
	@Test
	public void testQueryIfUserIsValid() {
		StaffInfo sinfo = staffInfoDao.QueryIfUserIsValid("admin1", HISPassword.enCode("123"));
		Assert.assertNotNull(sinfo);
	}

}
