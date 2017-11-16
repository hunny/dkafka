package com.qianfan123.dpos.data.quartz;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService.BatchHandler;
import com.qianfan123.dpos.data.dao.UuidHandleService;
import com.qianfan123.dpos.data.service.KafakaSenderService;

public abstract class AbstractUuidSenderService implements ApplicationContextAware {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private ApplicationContext applicationContext;

  private UuidHandleService uuidHandleService;

  @PostConstruct
  public void init() {
    uuidHandleService = applicationContext.getBean(getUuidHandleClass());
  }

  @Autowired
  private KafakaSenderService kafakaSenderService;

  public void execute(String dbName, String shop) {
    uuidHandleService.handle(new BatchHandler() {
      @Override
      public void handle(List<String> uuids, String... strings) {
        for (String uuid : uuids) {
          // 第一个参数是db，第二个参数是shopId
          String dbName = strings[0];
          String shop = strings[1];
          logger.debug("Job内部处理参数{}, {}, {}", uuid, dbName, shop);
          try {
            String value = getAsString(shop, uuid);
            if (StringUtils.isNotBlank(value)) {
              kafakaSenderService.send(shop, value);
            }
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

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
