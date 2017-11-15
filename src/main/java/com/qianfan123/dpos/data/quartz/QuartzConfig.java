package com.qianfan123.dpos.data.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

  @Bean
  public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext) {
    SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);

    // Set jobFactory to AutowiringSpringBeanJobFactory
    factoryBean.setJobFactory(jobFactory);
    return factoryBean;
  }

}
