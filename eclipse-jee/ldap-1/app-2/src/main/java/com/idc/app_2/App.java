package com.idc.app_2;

import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

/**
 * Hello world!
 *
 */
public class App {
	private static final String namedObject_1 = "uid=member1,ou=users,ou=system"; 
	private static final String namedObject_2 = "ou=users,ou=system"; 
	private static final String namedObject_3 = "uid=member99,ou=users,ou=system"; 
	private static final String[] requiredAttributes = { "uid", "cn", "sn", "givenName", "mail", "userPassword" };

	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
//		test4();
//		test5();
		test6();
	}
	@SuppressWarnings("unused")
	private static void test1() {
		DirContext context = LdapUtils.connect();
		LdapUtils.doLookup ("Test1-1", context, namedObject_1, requiredAttributes);
//		LdapUtils.doList ("Test1-2", context, namedObject_2, requiredAttributes);
	}
	@SuppressWarnings("unused")
	private static void test2() {
		DirContext context = LdapUtils.connect();
		LdapUtils.doList ("Test2-1", context, namedObject_2, requiredAttributes);
		LdapUtils.addUser(context);
		LdapUtils.doList ("Test2-2", context, namedObject_3, requiredAttributes);
	}
	@SuppressWarnings("unused")
	private static void test3() {
		DirContext context = LdapUtils.connect();
		LdapUtils.doFullLookup ("Test3", context, namedObject_1);
	}
	@SuppressWarnings("unused")
	private static void test4() {
		DirContext context = LdapUtils.connect();
		LdapUtils.doList ("Test4-1", context, namedObject_3, requiredAttributes);
		LdapUtils.deleteUser(context);
	}

	@SuppressWarnings("unused")
	private static void test5() {
		DirContext context = LdapUtils.connect();
		LdapUtils.doPasswordTest ("Test5", context, namedObject_1);
	}

	@SuppressWarnings("unused")
	private static void test6() {
		DirContext context = LdapUtils.connect();
		Attributes attrs = LdapUtils.getUser("Test6", context, namedObject_1);
		LdapUtils.displayAttributes ("Test6", attrs);
		LdapUtils.updateUser(context, attrs, namedObject_1);
		LdapUtils.doLookup ("Test6-1", context, namedObject_1, requiredAttributes);
	}
}
