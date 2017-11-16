package com.qianfan123.dpos.data.service.salereturn;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd123.dpos.api.salereturn.SaleReturn;
import com.hd123.dpos.api.salereturn.SaleReturnService;
import com.hd123.rumba.commons.biz.entity.EntityNotFoundException;
import com.qianfan123.dpos.data.common.DkafkaException;

@Component
public class KafkaSaleReturnService {

  @Autowired
  private SaleReturnService saleReturnService;

  @Autowired
  private ObjectMapper objectMapper;

  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    SaleReturn saleReturn = null;
    try {
      saleReturn = saleReturnService.get(shop, uuid, SaleReturn.ALL_PARTS);
      if (null == saleReturn) {
        throw new DkafkaException("根据门店[{0}]和UUID[{1}]查询SaleReturn无记录", shop, uuid);
      }
    } catch (EntityNotFoundException e) {
      throw new DkafkaException("根据门店[{0}]和UUID[{1}]查询SaleReturn无记录", shop, uuid);
    }

    KafkaSaleReturn kafkaSaleReturn = KafkaSaleReturn.TO.convert(saleReturn);
    kafkaSaleReturn.set_dcbustype("salereturn");

    String value = objectMapper.writeValueAsString(kafkaSaleReturn);
    return value;
  }

}
