package com.qianfan123.dpos.data.config.salereturn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.qianfan123.dpos.data.config.AbstractMQAliyunConfig;
import com.qianfan123.dpos.data.service.salereturn.SaleReturnListener;

@Configuration
public class SaleReturnMQAliyunConfig extends AbstractMQAliyunConfig<SaleReturnListener> {

  protected final String TOPIC_NAME = "ReturnChangeTopic-";
  protected final String QUEUE_NAME = "KafkaSaleReturnChangeQueue-";

  @Autowired
  private SaleReturnListener saleReturnListener;

  @Override
  public String getTopicName() {
    return TOPIC_NAME;
  }

  @Override
  public String getQueueName() {
    return QUEUE_NAME;
  }

  @Override
  public SaleReturnListener getListener() {
    return saleReturnListener;
  }

}
