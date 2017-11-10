package com.qianfan123.dpos.data.service.sale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hd123.rumba.mq.api.QueueMessage;
import com.hd123.rumba.mq.bus.MessageListener;

@Component
public class SaleListener implements MessageListener<QueueMessage> {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void consume(QueueMessage message, String queueName) throws Exception {
    logger.info("接收到消息[{}]，QueueName[{}]", message, queueName);
  }

}
