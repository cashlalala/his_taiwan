package org.his.dao;

import org.his.model.StaffInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StaffInfoDaoTest {

	private static StaffInfoDao staffInfoDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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

}
