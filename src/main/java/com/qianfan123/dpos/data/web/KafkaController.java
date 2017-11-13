package com.qianfan123.dpos.data.web;

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

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.common.RestResp;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private KafkaTemplate kafkaTemplate;

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

}
