package com.qianfan123.dpos.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.qianfan123.dpos.data.config.swagger.EnableSwagger2Api;

@SpringBootApplication
@EnableSwagger2Api
@EnableCaching
public class Application {

  public static void main(String[] args) {
    
    SpringApplication.run(Application.class, args);
    
  }

}
