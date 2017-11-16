package com.qianfan123.dpos.data.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaReceiver {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private Environment environment;
  
  @KafkaListener(topics = "${kafka.topic.name}")
  public void receive(ConsumerRecord data) {
    logger.debug("Enviroment '{}', received payload = '{}'", //
        environment.getActiveProfiles(), data);
  }

}
