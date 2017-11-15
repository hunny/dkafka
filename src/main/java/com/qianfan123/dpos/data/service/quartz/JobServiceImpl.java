package com.qianfan123.dpos.data.service.quartz;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.qianfan123.dpos.data.common.DkafkaException;

@Service
public class JobServiceImpl implements JobService, ApplicationContextAware {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Scheduler scheduler;

  private ApplicationContext applicationContext;

  public JobServiceImpl(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  @PostConstruct
  public void init() throws SchedulerException {
    this.scheduler.getContext().put(APPLICATION_CONTEXT, applicationContext);
  }

  @Override
  public <T extends Job> void startNow(String name, String group, JobDataMap jobDataMap,
      Class<T> clazz, boolean replace) throws DkafkaException {

    logger.debug("start job group '{}', name '{}', replace '{}', job class '{}', data '{}'", //
        group, name, replace, clazz.getName(), jobDataMap);

    JobDetail jobDetail = JobBuilder.newJob(clazz) //
        .withIdentity(name, group) //
        .usingJobData(jobDataMap) //
        .build();

    Trigger trigger = TriggerBuilder.newTrigger() //
        .withIdentity(name, group) //
        .withSchedule( //
            SimpleScheduleBuilder.simpleSchedule() //
                .withMisfireHandlingInstructionNextWithExistingCount() //
        ) //
        .startNow() //
        .usingJobData(jobDataMap) //
        .build();

    Set<Trigger> triggers = new HashSet<>();
    triggers.add(trigger);
    try {
      scheduler.scheduleJob(jobDetail, triggers, replace);
    } catch (SchedulerException e) {
      logger.error("Schedule error {}", e.getMessage());
      throw new DkafkaException(e);
    }

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
