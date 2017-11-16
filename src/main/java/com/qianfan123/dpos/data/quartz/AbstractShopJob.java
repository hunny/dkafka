package com.qianfan123.dpos.data.quartz;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.hd123.rumba.commons.lang.Assert;
import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService.BatchHandler;
import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.ShopService;
import com.qianfan123.dpos.data.service.quartz.JobService;

public abstract class AbstractShopJob implements Job {

  private final Logger logger = LoggerFactory.getLogger(AbstractShopJob.class);

  @Autowired
  private ShopService shopService;

  @Autowired
  private JobService jobService;

  private QueryBatchable queryBatchable;

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    JobDataMap jobDateMap = arg0.getMergedJobDataMap();
    ApplicationContext applicationContext = null;
    try {
      applicationContext = (ApplicationContext) arg0.getScheduler().getContext()
          .get(JobService.APPLICATION_CONTEXT);
    } catch (SchedulerException e) {
      e.printStackTrace();
      logger.error("{}", e.getMessage());
      throw new JobExecutionException(e);
    }
    logger.info("Dynamic Schedule Job Success. {}", arg0);
    logger.info("Dynamic jobDateMap {}", jobDateMap);
    if (null == queryBatchable) {
      queryBatchable = applicationContext.getBean(getBatchableClass());
    }
    shopService.handle(new BatchHandler() {
      @Override
      public void handle(List<String> shops, String... strings) {
        Assert.notEmpty(strings);

        String dbName = strings[0];
        logger.debug("处理数据库[{}]的所有门店信息。", dbName);
        for (String shop : shops) {
          JobDataMap JobDataMap = new JobDataMap();
          JobDataMap.put(ShopService.DB_NAME, dbName);
          JobDataMap.put(ShopService.SHOP_ID, shop);
          try {
            jobService.startNow(shop, dbName, JobDataMap, getUuidJobClass(), false);
          } catch (DkafkaException e) {
            e.printStackTrace();
            logger.error("启动数据库[{}]门店[{}]的处理任务失败:{}。", dbName, shop, e.getMessage());
          }
        }
      }
    }, queryBatchable);
  }

  public abstract Class<? extends QueryBatchable> getBatchableClass();

  public abstract Class<? extends Job> getUuidJobClass();

}
