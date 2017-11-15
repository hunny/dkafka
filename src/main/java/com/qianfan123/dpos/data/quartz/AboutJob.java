package com.qianfan123.dpos.data.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qianfan123.dpos.data.service.about.AboutService;

public class AboutJob implements Job {

  private final Logger logger = LoggerFactory.getLogger(AboutJob.class);
  
  @Autowired
  private AboutService aboutService;
  
  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    JobDataMap jobDateMap = arg0.getMergedJobDataMap();
    logger.info("Demo Dynamic Schedule Job Success. {}", arg0);
    logger.info("Demo Dynamic jobDateMap {}", jobDateMap);
    logger.info("Demo Dynamic about {}", aboutService.about());
  }

}
