package org.his.dao;

import static org.junit.Assert.*;

import org.his.model.PatientsInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PatientsInfoDaoTest {

	private static PatientsInfoDao patientsInfoDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		patientsInfoDao = new PatientsInfoDao();
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
	public void testMerge() {
	}

	@Test
	public void testDeleteAutoGenUser() {
	}

	@Test
	public void testRemove() {
	}

	@Test
	public void testQueryPatientInfoById() {
		PatientsInfo patientsInfo = patientsInfoDao
				.QueryPatientInfoById("c8611f99-083e-11e4-bfdc-000c29285422");
		Assert.assertNotNull(patientsInfo);
	}

	@Test
	public void testGetExistedPatients() {
	}

	@Test
	public void testGetPatientCount() {
	}

	@Test
	public void testGetExistedPatientsStringIntInt() {
	}

}
