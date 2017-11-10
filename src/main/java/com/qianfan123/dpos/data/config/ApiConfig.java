package com.qianfan123.dpos.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ //
    "classpath:META-INF/cmdb-api/cxf3.0/*.xml", //
    "classpath:META-INF/dpos-api/route/cxf3.0/sale.xml", //
    "classpath:META-INF/dpos-api/route/cxf3.0/saleReturn.xml", //
    "classpath:rumba-mq-aliyun.xml", //
}) //
public class ApiConfig {

  //Do Nothing
  
}
