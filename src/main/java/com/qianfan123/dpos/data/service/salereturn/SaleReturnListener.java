package com.qianfan123.dpos.data.service.salereturn;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.service.DataReceiveListener;

@Component
public class SaleReturnListener extends DataReceiveListener {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private KafkaSaleReturnService kafkaSaleReturnService;

  @Override
  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    logger.debug("转化SaleReturn数据{},{}", shop, uuid);
    return kafkaSaleReturnService.getAsString(shop, uuid);
  }

}
