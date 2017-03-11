package com.idc.app_1;

import org.apache.directory.ldap.client.api.LdapConnection;

/**
 * ref:
 * https://directory.apache.org/api/user-guide.html
 *
 */
public class App {
	private static final String DN_1 = "uid=member1,ou=users,ou=system"; 
	private static final String DN_2 = "uid=member99,ou=users,ou=system"; 

	public static void main(String[] args) throws Exception {
		test5();
	}
	@SuppressWarnings("unused")
	private static void test1() throws Exception {
		LdapConnection connection = LdapUtilities.connect();	
		connection.close();
	}
	@SuppressWarnings("unused")
	private static void test2() throws Exception {
		LdapConnection connection = LdapUtilities.connect();	
		LdapUtilities.getUser ("Test2", connection, DN_1);
		connection.close();
	}
	@SuppressWarnings("unused")
	private static void test3() throws Exception {
		LdapConnection connection = LdapUtilities.connect();	
		LdapUtilities.addUser ("Test3", connection, DN_2);
		connection.close();
	}
	@SuppressWarnings("unused")
	private static void test4() throws Exception {
		LdapConnection connection = LdapUtilities.connect();	
		LdapUtilities.deleteUser ("Test4", connection, DN_2);
		connection.close();
	}
	@SuppressWarnings("unused")
	private static void test5() throws Exception {
		LdapConnection connection = LdapUtilities.connect();	
		LdapUtilities.updateUser ("Test5", connection, DN_2);
		connection.close();
	}
}
