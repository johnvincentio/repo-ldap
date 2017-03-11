package com.idc.app_1;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class Test1 extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(Test1.class);
	private static final String USER = "uid=admin,ou=system";
	private static final String PWD = "secret";

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public Test1(String testName) {
		super(testName);
	}

	public void testApp1() throws IOException {
		log.info("starting testApp1");
		LdapConnection connection = new LdapNetworkConnection ("localhost", 10389);
		assertFalse (connection.isConnected());
		assertFalse (connection.isAuthenticated());

		connection.close();
		assertFalse (connection.isConnected());
		log.info("finished testApp1");
	}

	public void testApp2() throws IOException, LdapException {
		log.info("starting testApp2");
		LdapConnection connection = new LdapNetworkConnection ("localhost", 10389);
		assertFalse (connection.isConnected());
		assertFalse (connection.isAuthenticated());
		
		connection.bind ("uid=admin,ou=system", "secret");
		assertTrue (connection.isConnected());
		assertTrue (connection.isAuthenticated());

		connection.unBind();
		assertFalse (connection.isConnected());
		assertFalse (connection.isAuthenticated());

		connection.close();
		assertFalse (connection.isConnected());
		log.info("finished testApp2");
	}

}
