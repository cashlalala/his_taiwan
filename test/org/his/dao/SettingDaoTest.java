package org.his.dao;

import static org.junit.Assert.*;

import org.his.JPAUtil;
import org.his.model.Setting;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SettingDaoTest {
	
	private static SettingDao settingDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JPAUtil.getEntityManagerFactory("UT_hospital");
		settingDao = new SettingDao();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQuerySettingById() {
		Setting set = settingDao.QuerySettingById(1);
		Assert.assertNotNull(set);
	}

}
