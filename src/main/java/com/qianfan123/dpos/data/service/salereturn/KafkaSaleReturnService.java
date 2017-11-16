package com.qianfan123.dpos.data.service.salereturn;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd123.dpos.api.salereturn.SaleReturn;
import com.hd123.dpos.api.salereturn.SaleReturnService;
import com.qianfan123.dpos.data.common.DkafkaException;

@Component
public class KafkaSaleReturnService {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SaleReturnService saleReturnService;

  @Autowired
  private ObjectMapper objectMapper;

  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    SaleReturn saleReturn = null;
    try {
      saleReturn = saleReturnService.get(shop, uuid, SaleReturn.ALL_PARTS);
      if (null == saleReturn) {
        logger.info("根据门店[{}]和UUID[{}]查询SaleReturn无记录", shop, uuid);
        return null;
      }
    } catch (Exception e) {
      logger.error("根据门店[{}]和UUID[{}]查询SaleReturn时异常:{}", shop, uuid, e.getMessage());
      return null;
    }

    KafkaSaleReturn kafkaSaleReturn = KafkaSaleReturn.TO.convert(saleReturn);
    kafkaSaleReturn.set_dcbustype("salereturn");

    String value = objectMapper.writeValueAsString(kafkaSaleReturn);
    return value;
  }

}
