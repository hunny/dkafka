package com.qianfan123.dpos.data.config.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.qianfan123.dpos.data.config.AbstractMQAliyunConfig;
import com.qianfan123.dpos.data.service.sale.SaleListener;

@Configuration
public class SaleMQAliyunConfig extends AbstractMQAliyunConfig<SaleListener> {

  protected final String TOPIC_NAME = "SaleChangeTopic-";
  protected final String QUEUE_NAME = "KafkaSaleChangeQueue-";

  @Autowired
  private SaleListener saleListener;

  @Override
  public String getTopicName() {
    return TOPIC_NAME;
  }

  @Override
  public String getQueueName() {
    return QUEUE_NAME;
  }

  @Override
  public SaleListener getListener() {
    return saleListener;
  }

}
