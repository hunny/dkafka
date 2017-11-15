package com.qianfan123.dpos.data.service.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;

import com.qianfan123.dpos.data.common.DkafkaException;

public interface JobService {

  <T extends Job> void startNow(String name, String group, //
      JobDataMap jobDataMap, Class<T> clazz, boolean replace) throws DkafkaException;

}
