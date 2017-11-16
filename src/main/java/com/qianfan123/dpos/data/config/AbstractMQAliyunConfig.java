package com.qianfan123.dpos.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.hd123.rumba.mq.aliyun.spring.MNSQueueFactoryBean;
import com.hd123.rumba.mq.aliyun.spring.MNSSubscriptionConfigurer;
import com.hd123.rumba.mq.aliyun.spring.MNSTopicFactoryBean;
import com.hd123.rumba.mq.api.MQConnection;
import com.hd123.rumba.mq.api.spring.QueueFactoryBean;
import com.hd123.rumba.mq.api.spring.SubscriptionConfigurer;
import com.hd123.rumba.mq.api.spring.TopicFactoryBean;
import com.hd123.rumba.mq.bus.MessageBus;
import com.hd123.rumba.mq.bus.MessageListener;
import com.hd123.rumba.mq.bus.MessageListenerBinder;

public abstract class AbstractMQAliyunConfig<T extends MessageListener> {

  public abstract String getTopicName();

  public abstract String getQueueName();

  @Value("${dkafka.mns.stuffix:dev}")
  private String stuffix;

  @Bean
  public TopicFactoryBean topic(MQConnection connection) { // 销售单变更 主题
    MNSTopicFactoryBean topicFactoryBean = new MNSTopicFactoryBean();
    topicFactoryBean.setConnection(connection);
    topicFactoryBean.setAutoUpdateService(true);
    topicFactoryBean.setTopicName(getTopicName() + stuffix);
    return topicFactoryBean;
  }

  @Bean
  public QueueFactoryBean queue(MQConnection connection) {// 队列
    MNSQueueFactoryBean queueFactoryBean = new MNSQueueFactoryBean();
    queueFactoryBean.setConnection(connection);
    queueFactoryBean.setAutoUpdateService(true);
    queueFactoryBean.setQueueName(getQueueName() + stuffix);
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
  
  public String getStuffix() {
    return stuffix;
  }
  
  @Bean
  public MessageListenerBinder bind(MessageBus bus) {
    MessageListenerBinder messageListenerBinder = new MessageListenerBinder();
    messageListenerBinder.setBus(bus);
    messageListenerBinder.setQueueName(getQueueName() + getStuffix());
    messageListenerBinder.setListener(getListener());
    return messageListenerBinder;
  }
  
  public abstract T getListener();

}
