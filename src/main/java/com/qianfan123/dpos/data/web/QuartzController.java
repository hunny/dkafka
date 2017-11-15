package com.qianfan123.dpos.data.web;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hd123.rumba.commons.lang.Assert;
import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.service.quartz.JobService;

@RestController
@RequestMapping("/quartz")
public class QuartzController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final JobService jobService;

  public QuartzController(JobService jobService) {
    this.jobService = jobService;
  }

  @PostMapping(path = "/start/{group}/now/{name}/{replace}")
  public ResponseEntity<Void> startNow(@PathVariable String group, //
      @PathVariable("name") String name, //
      @PathVariable("replace") boolean replace, //
      @RequestParam("clazz") String clazzName, //
      @RequestBody Map<String, Object> data) throws DkafkaException {

    Assert.notNull(data, "data");

    logger.debug("start job group '{}', name '{}', replace '{}', job class '{}', data '{}'", //
        group, name, replace, clazzName, data);

    Class<? extends Job> clazz = null;
    try {
      clazz = (Class<? extends Job>) Class.forName(clazzName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new DkafkaException(e);
    }
    JobDataMap jobDataMap = new JobDataMap(data);

    jobService.startNow(name, group, jobDataMap, clazz, replace);

    return ResponseEntity.noContent().build();
  }

}
