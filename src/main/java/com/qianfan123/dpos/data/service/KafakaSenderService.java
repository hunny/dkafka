package com.qianfan123.dpos.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafakaSenderService {

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Value("${kafka.topic.name}")
  private String topic;

  public <V, K> void send(String topic, V key, K value) {
    kafkaTemplate.send(topic, key, value);
  }

  public <V, K> void send(Object key, Object value) {
    kafkaTemplate.send(topic, key, value);
  }

  public String getTopic() {
    return topic;
  }

}
