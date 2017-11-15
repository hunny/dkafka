package com.qianfan123.dpos.data.quartz.sale;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.hd123.rumba.commons.lang.Assert;
import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService.Handler;
import com.qianfan123.dpos.data.dao.ShopService;
import com.qianfan123.dpos.data.dao.sale.SaleUuidService;
import com.qianfan123.dpos.data.service.quartz.JobService;

public class SaleUuidJob implements Job {

  private final Logger logger = LoggerFactory.getLogger(SaleUuidJob.class);

  private SaleUuidService saleUuidService;

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    JobDataMap jobDateMap = arg0.getMergedJobDataMap();
    logger.info("Demo Dynamic jobDateMap {}", jobDateMap);
    String dbName = jobDateMap.getString(ShopService.DB_NAME);
    String shop = jobDateMap.getString(ShopService.SHOP_ID);

    Assert.notNull(dbName);
    Assert.notNull(shop);

    ApplicationContext applicationContext = null;
    try {
      applicationContext = (ApplicationContext) arg0.getScheduler().getContext()
          .get(JobService.APPLICATION_CONTEXT);
      saleUuidService = applicationContext.getBean(SaleUuidService.class);
    } catch (SchedulerException e) {
      e.printStackTrace();
      logger.error("{}", e.getMessage());
      throw new JobExecutionException(e);
    }

    saleUuidService.handle(new Handler() {
      @Override
      public void handle(List<String> uuids, String... strings) {
        for (String uuid : uuids) {
          logger.info("Job内部处理参数{}, {}, {}", uuid, strings[0], strings[1]);
        }
      }
    }, dbName, shop);
  }

}
