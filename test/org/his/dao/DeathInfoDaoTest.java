package org.his.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.his.model.DeathInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DeathInfoDaoTest {

	private static DeathInfoDao deathInfoDao;
	
	private static String uuid;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		deathInfoDao = new DeathInfoDao();
		uuid = UUID.randomUUID().toString();
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPersist() {
		try {
			DeathInfo deathInfo = new DeathInfo();
			deathInfo.setCause("I just died");
			deathInfo.setDateOfDeath(new Date());
			deathInfo.setGuid(uuid);	
			deathInfo.setIndicator("A");
			
			DeathInfo managedDeathInfo = deathInfoDao.merge(deathInfo);
			deathInfoDao.persist(managedDeathInfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}

	}


	@Test
	public void testRemove() {
		try {
			DeathInfo deathInfo = deathInfoDao.QueryDeathInfoById(uuid);
			deathInfoDao.remove(deathInfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
