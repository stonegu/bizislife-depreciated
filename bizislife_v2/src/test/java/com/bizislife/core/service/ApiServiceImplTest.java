package com.bizislife.core.service;

import static org.junit.Assert.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:root-context.xml",
		"classpath:servlet-context.xml" })
public class ApiServiceImplTest {
	
	@InjectMocks
	@Autowired
	ApiService apiService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetForObjectFromHttps() {
		String urlOverHttps = "https://staging.itemcentre.gs1ca.org/webservicev2/workflowstatsservice.svc/Restservice/getTradingPartnerWorkflowData/?vendorUserId=27D3FF22-A87E-450F-8103-AE9118F6B4B5";
		List<String> trustedIps = new ArrayList<>();
		trustedIps.add("staging.itemcentre.gs1ca.org");
		
		try {
			String response = apiService.getForObjectFromHttps(urlOverHttps, trustedIps, String.class);
			
			assertNotNull(response);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
