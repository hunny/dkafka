package com.qianfan123.dpos.data.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hd123.rumba.commons.lang.Assert;
import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.common.RestResp;
import com.qianfan123.dpos.data.service.sale.KafkaSaleService;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Autowired
  private KafkaSaleService kafkaSaleService;

  @RequestMapping(value = "/send/message", method = RequestMethod.POST)
  public RestResp<String> sendSimple(@RequestParam("topic") String topic, //
      @RequestBody String message) throws DkafkaException {
    logger.info("准备发送向[{}]发送消息[{}]", topic, message);
    kafkaTemplate.send(topic, message);
    return new RestResp<String>(message);
  }

  @RequestMapping(value = "/send/key/value", method = RequestMethod.POST)
  public RestResp<Map<String, String>> sendMap(@RequestParam("topic") String topic, //
      @RequestParam("key") String key, //
      @RequestBody String value) throws DkafkaException {
    logger.info("准备发送向[{}]发送消息[{}]", topic, value);
    kafkaTemplate.send(topic, key, value);
    Map<String, String> map = new HashMap<String, String>();
    map.put(key, value);
    return new RestResp<Map<String, String>>(map);
  }

  @RequestMapping(value = "/send/sale", method = {
      RequestMethod.GET, RequestMethod.POST })
  public RestResp<String> sendSale( //
      @RequestParam(name = "topic", defaultValue = "${kafka.topic.name}") String topic, //
      @RequestParam("shop") String shop, //
      @RequestParam("uuid") String uuid) throws DkafkaException {
    Assert.hasText(topic);
    Assert.notNull(shop);
    Assert.notNull(uuid);

    try {
      String value = kafkaSaleService.getAsString(shop, uuid);
      logger.info("准备向[{}]发送消息，key[{}], value[{}]", topic, shop, value);
      kafkaTemplate.send(topic, shop, value);
      return new RestResp<String>(value);
    } catch (DkafkaException | IOException e) {
      return new RestResp(false, e.getMessage());
    }

  }

}
