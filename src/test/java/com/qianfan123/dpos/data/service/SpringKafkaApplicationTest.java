package com.qianfan123.dpos.data.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringKafkaApplicationTest {

  protected final static String HELLOWORLD_TOPIC = "helloworld.t";

  @Autowired
  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

//  @Autowired
//  private KafkaReceiver kafkaReceiver;

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @ClassRule
  public static KafkaEmbedded KAFKA_EMBEDDED = //
      new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);

  @Before
  public void runBeforeTestMethod() throws Exception {
    // wait until all the partitions are assigned
    for (MessageListenerContainer messageListenerContainer : //
    kafkaListenerEndpointRegistry.getListenerContainers()) {
      ContainerTestUtils.waitForAssignment(messageListenerContainer,
          KAFKA_EMBEDDED.getPartitionsPerTopic());
    }
  }

  @Test
  public void testKafkaReceive() throws Exception {
//    kafkaTemplate.send(HELLOWORLD_TOPIC, "Hello Spring Kafka!");
//
//    kafkaReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
//    assertThat(kafkaReceiver.getLatch().getCount()).isEqualTo(0);
  }
}
