package com.qianfan123.dpos.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hd123.rumba.mq.aliyun.spring.MNSQueueFactoryBean;
import com.hd123.rumba.mq.aliyun.spring.MNSSubscriptionConfigurer;
import com.hd123.rumba.mq.aliyun.spring.MNSTopicFactoryBean;
import com.hd123.rumba.mq.api.MQConnection;
import com.hd123.rumba.mq.api.spring.QueueFactoryBean;
import com.hd123.rumba.mq.api.spring.SubscriptionConfigurer;
import com.hd123.rumba.mq.api.spring.TopicFactoryBean;
import com.hd123.rumba.mq.bus.MessageBus;
import com.hd123.rumba.mq.bus.MessageListenerBinder;
import com.qianfan123.dpos.data.service.sale.SaleListener;

@Configuration
public class SaleMQAliyunConfig {

  protected final String TOPIC_NAME = "SaleChangeTopic-";
  protected final String QUEUE_NAME = "KafkaSaleChangeQueue-";

  @Value("${dkafka.mns.stuffix:dev}")
  private String stuffix;

  @Bean
  public TopicFactoryBean topic(MQConnection connection) { // 销售单变更 主题
    MNSTopicFactoryBean topicFactoryBean = new MNSTopicFactoryBean();
    topicFactoryBean.setConnection(connection);
    topicFactoryBean.setAutoUpdateService(true);
    topicFactoryBean.setTopicName(TOPIC_NAME + stuffix);
    return topicFactoryBean;
  }

  @Bean
  public QueueFactoryBean queue(MQConnection connection) {// 队列
    MNSQueueFactoryBean queueFactoryBean = new MNSQueueFactoryBean();
    queueFactoryBean.setConnection(connection);
    queueFactoryBean.setAutoUpdateService(true);
    queueFactoryBean.setQueueName(QUEUE_NAME + stuffix);
    return queueFactoryBean;
  }

  @Bean
  public SubscriptionConfigurer subscript(MQConnection connection, MNSTopicFactoryBean topic, //
      MNSQueueFactoryBean queue) throws Exception {
    MNSSubscriptionConfigurer subscriptionConfigurer = new MNSSubscriptionConfigurer();
    subscriptionConfigurer.setConnection(connection);
    subscriptionConfigurer.setTopic(topic.getObject());
    subscriptionConfigurer.setQueue(queue.getObject());
    return subscriptionConfigurer;
  }

  @Bean
  public MessageBus bus(MQConnection connection) {
    MessageBus bus = new MessageBus();
    bus.setConnection(connection);
    return bus;
  }
  
  @Bean
  public MessageListenerBinder bind(MessageBus bus, SaleListener listener) {
    MessageListenerBinder messageListenerBinder = new MessageListenerBinder();
    messageListenerBinder.setBus(bus);
    messageListenerBinder.setQueueName(QUEUE_NAME + stuffix);
    messageListenerBinder.setListener(listener);
    return messageListenerBinder;
  }

}
