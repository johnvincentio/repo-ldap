package com.idc.app_1;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	protected void setUp() {
		System.out.println("AllTests::setUp");
	}
	protected void tearDown() {
		System.out.println("AllTests::tearDown");
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite (Test1.class);
		suite.addTestSuite (Test2.class);
		return suite;
	}
}
