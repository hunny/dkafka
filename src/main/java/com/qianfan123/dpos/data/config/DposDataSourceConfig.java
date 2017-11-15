package com.qianfan123.dpos.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ //
  "classpath:META-INF/datasource/dpos/datasource.xml", //
  "classpath:META-INF/datasource/dpos/rumba-paging.xml", //
}) //
public class DposDataSourceConfig {

  //Do Nothing
  
}
