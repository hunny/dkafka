package com.qianfan123.dpos.data.service.about;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.qianfan123.dpos.data.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class) // 指定spring-boot的启动类
@WebAppConfiguration
public class AboutServiceTest {

  @Autowired
  private AboutService aboutService;

  @Test
  public void testAbout() {
    
    String expected = "DPOS data to kafka.";
    String actual = aboutService.about();
    Assert.assertTrue("数据一致", expected.equals(actual));
    Assert.assertFalse("数据不一致", !expected.equals(actual));
    
  }

}
