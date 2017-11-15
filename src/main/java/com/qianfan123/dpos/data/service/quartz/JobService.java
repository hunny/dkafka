package com.qianfan123.dpos.data.service.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.springframework.context.ApplicationContext;

import com.qianfan123.dpos.data.common.DkafkaException;

public interface JobService {
  
  public static final String APPLICATION_CONTEXT = ApplicationContext.class.getName();

  <T extends Job> void startNow(String name, String group, //
      JobDataMap jobDataMap, Class<T> clazz, boolean replace) throws DkafkaException;

}
