package com.qianfan123.dpos.data.quartz.sale;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.dao.UuidHandleService;
import com.qianfan123.dpos.data.dao.sale.SaleUuidHandleService;
import com.qianfan123.dpos.data.quartz.AbstractUuidJob;
import com.qianfan123.dpos.data.service.sale.KafkaSaleService;

@Deprecated
public class SaleUuidSenderJob extends AbstractUuidJob {

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
