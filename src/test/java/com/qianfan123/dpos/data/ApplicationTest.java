package com.qianfan123.dpos.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.qianfan123.dpos.data.service.about.AboutServiceTest;
import com.qianfan123.dpos.data.web.AboutControllerTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AboutServiceTest.class, AboutControllerTest.class })
public class ApplicationTest extends TestCase {
  /**
   * Create the test case
   *
   * @param testName
   *          name of the test case
   */
  public ApplicationTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(ApplicationTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp() {
    assertTrue(true);
  }
}
