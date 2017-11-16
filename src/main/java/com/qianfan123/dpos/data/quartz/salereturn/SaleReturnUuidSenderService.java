package com.qianfan123.dpos.data.quartz.salereturn;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.dao.UuidHandleService;
import com.qianfan123.dpos.data.dao.salereturn.SaleReturnUuidHandleService;
import com.qianfan123.dpos.data.quartz.AbstractUuidSenderService;
import com.qianfan123.dpos.data.service.salereturn.KafkaSaleReturnService;

@Component
public class SaleReturnUuidSenderService extends AbstractUuidSenderService {

  @Autowired
  private KafkaSaleReturnService kafkaSaleReturnService;

  @Override
  public Class<? extends UuidHandleService> getUuidHandleClass() {
    return SaleReturnUuidHandleService.class;
  }

  @Override
  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    return kafkaSaleReturnService.getAsString(shop, uuid);
  }
  
}
