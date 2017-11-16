package com.qianfan123.dpos.data.quartz.sale;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.dao.UuidHandleService;
import com.qianfan123.dpos.data.dao.sale.SaleUuidHandleService;
import com.qianfan123.dpos.data.quartz.AbstractUuidSenderService;
import com.qianfan123.dpos.data.service.sale.KafkaSaleService;

@Component
public class SaleUuidSenderService extends AbstractUuidSenderService {

  @Autowired
  private KafkaSaleService kafkaSaleService;

  @Override
  public Class<? extends UuidHandleService> getUuidHandleClass() {
    return SaleUuidHandleService.class;
  }

  @Override
  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    return kafkaSaleService.getAsString(shop, uuid);
  }

  
}
