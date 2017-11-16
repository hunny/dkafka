package com.qianfan123.dpos.data.dao;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShopService extends AbstraceBatchQueryService {

  public static final String DB_NAME = "dbName";
  public static final String SHOP_ID = "shopId";
  
  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private DbCommonDao dbCommonDao;
  
  @Value("${dkafka.db.handle.threadnumber:5}")
  private Integer dbthreadnumber;
  
  /**
   * @param handler
   * @param shopListable, 产生shops依赖查询
   */
  public void handle(final BatchHandler handler, final QueryBatchable queryBatchable) {
    if (null == handler) {
      return;
    }
    List<String> dbs = dbCommonDao.getShopDbNames();
    ExecutorService executorService = Executors.newFixedThreadPool(dbthreadnumber);
    final CountDownLatch latch = new CountDownLatch(dbs.size());
    for (final String db : dbs) {
      executorService.submit(new Runnable() {
        @Override
        public void run() {
          try {
            handleBy(handler, queryBatchable, db);
          } finally {
            logger.info("=====>完成数据库[{}]类型为[{}]的数据处理任务。", db, queryBatchable.getClass().getName());
            latch.countDown();
          }
        }
      });
    }
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
      logger.info("=====>数据类型为[{}]的数据处理任务异常{}。", queryBatchable.getClass().getName(), e.getMessage());
    }
    logger.info("=====>完成数据类型为[{}]的数据处理任务。", queryBatchable.getClass().getName());
  }

}
