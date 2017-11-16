package com.qianfan123.dpos.data.quartz;

import java.io.IOException;
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
import com.qianfan123.dpos.data.dao.ShopService;
import com.qianfan123.dpos.data.dao.UuidHandleService;
import com.qianfan123.dpos.data.service.KafakaSenderService;
import com.qianfan123.dpos.data.service.quartz.JobService;

public abstract class AbstractUuidJob implements Job {

  private final Logger logger = LoggerFactory.getLogger(AbstractUuidJob.class);

  private UuidHandleService uuidHandleService;

  @Autowired
  private KafakaSenderService kafakaSenderService;

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    JobDataMap jobDateMap = arg0.getMergedJobDataMap();
    logger.debug("jobDateMap {}", jobDateMap);
    String dbName = jobDateMap.getString(ShopService.DB_NAME);
    String shop = jobDateMap.getString(ShopService.SHOP_ID);

    Assert.notNull(dbName);
    Assert.notNull(shop);

    ApplicationContext applicationContext = null;
    try {
      applicationContext = (ApplicationContext) arg0.getScheduler().getContext()
          .get(JobService.APPLICATION_CONTEXT);
      uuidHandleService = applicationContext.getBean(getUuidHandleClass());
    } catch (SchedulerException e) {
      e.printStackTrace();
      logger.error("{}", e.getMessage());
      throw new JobExecutionException(e);
    }

    uuidHandleService.handle(new BatchHandler() {
      @Override
      public void handle(List<String> uuids, String... strings) {
        for (String uuid : uuids) {
          // 第一个参数是db，第二个参数是shopId
          String dbName = strings[0];
          String shop = strings[1];
          logger.debug("Job内部处理参数{}, {}, {}", uuid, dbName, shop);
          try {
            kafakaSenderService.send(shop, getAsString(shop, uuid));
          } catch (DkafkaException | IOException e) {
            e.printStackTrace();
            logger.error("kafka send sale message failure: {}", e.getMessage());
          }
        }
      }
    }, dbName, shop);
  }

  public abstract Class<? extends UuidHandleService> getUuidHandleClass();

  public abstract String getAsString(String shop, String uuid) throws DkafkaException, IOException;

}
