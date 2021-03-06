package com.qianfan123.dpos.data.service.sale;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.service.DataReceiveListener;

@Component
public class SaleListener extends DataReceiveListener {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private KafkaSaleService kafkaSaleService;

  @Override
  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    logger.debug("转化Sale数据{},{}", shop, uuid);
    return kafkaSaleService.getAsString(shop, uuid);
  }

}
