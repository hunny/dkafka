package com.qianfan123.dpos.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.qianfan123.dpos.data.config.swagger.EnableSwagger2Api;

@SpringBootApplication
@EnableSwagger2Api
public class Application {

  public static void main(String[] args) {
    
    SpringApplication.run(Application.class, args);
    
  }

}
