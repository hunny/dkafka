package com.qianfan123.dpos.data.quartz.salereturn;

import java.io.IOException;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.dao.UuidHandleService;
import com.qianfan123.dpos.data.dao.salereturn.SaleReturnUuidHandleService;
import com.qianfan123.dpos.data.quartz.AbstractUuidJob;
import com.qianfan123.dpos.data.service.salereturn.KafkaSaleReturnService;

@DisallowConcurrentExecution
public class SaleReturnUuidSenderJob extends AbstractUuidJob {

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
