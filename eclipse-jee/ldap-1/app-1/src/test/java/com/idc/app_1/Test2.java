package com.idc.app_1;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class Test2 extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(Test2.class);
	private static final String USER = "uid=admin,ou=system";
	private static final String PWD = "secret";

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public Test2(String testName) {
		super(testName);
	}

	public void testApp1() throws IOException {
		log.info("starting testApp1");
		log.info("finished testApp1");
	}
	
	public void testApp2() {
		log.info("starting testApp2");
		log.info("finished testApp2");
	}

}
