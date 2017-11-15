package com.qianfan123.dpos.data.service.sale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hd123.dpos.api.commons.DposMessageSyntaxException;
import com.hd123.rumba.commons.json.JsonObject;
import com.hd123.rumba.mq.api.QueueMessage;
import com.hd123.rumba.mq.bus.MessageListener;
import com.qianfan123.dpos.data.service.KafakaSenderService;

@Component
public class SaleListener implements MessageListener<QueueMessage> {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private KafkaSaleService kafkaSaleService;

  @Autowired
  private KafakaSenderService kafakaSenderService;

  @Value("${kafka.rumbamq.message.ignore:false}")
  private boolean ignore;

  @Override
  public void consume(QueueMessage message, String queueName) throws Exception {
    logger.info("接收到消息[{}]，QueueName[{}]", message, queueName);

    if (ignore) {
      logger.info("Rumba-MQ Message Ignore: {}", ignore);
      return;
    }

    String body = message.getBody();
    if (StringUtils.isBlank(body)) {
      return;
    }

    String shop = null;
    String uuid = null;
    try {
      // check message
      JsonObject json = new JsonObject(body);
      shop = json.optString("shop");
      uuid = json.optString("uuid");

      if (StringUtils.isBlank(shop)) {
        throw new DposMessageSyntaxException("消息格式错误, shop is blank.");
      }

      String value = kafkaSaleService.getAsString(shop, uuid);
      logger.debug("准备向[{}]发送消息，key[{}], value[{}]", //
          kafakaSenderService.getTopic(), shop, value);
      kafakaSenderService.send(shop, value);

    } catch (Exception e) {
      if (e instanceof DposMessageSyntaxException) {
        logger.warn("消息格式错误, 消息[{}]，QueueName[{}], 将忽略", message, queueName);
        return;
      }
      throw e;
    }

  }

}
