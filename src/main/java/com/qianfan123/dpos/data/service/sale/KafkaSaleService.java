package com.qianfan123.dpos.data.service.sale;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd123.dpos.api.sale.Sale;
import com.hd123.dpos.api.sale.SaleService;
import com.qianfan123.dpos.data.common.DkafkaException;

@Component
public class KafkaSaleService {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private SaleService saleService;

  @Autowired
  private ObjectMapper objectMapper;

  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    Sale sale = null;
    try {
      sale = saleService.get(shop, uuid, Sale.ALL_PARTS);
      if (null == sale) {
        logger.info("根据门店[{}]和UUID[{}]查询Sale无记录", shop, uuid);
        return null;
      }
    } catch (Exception e) {
      logger.error("根据门店[{}]和UUID[{}]查询Sale时异常:{}", shop, uuid, e.getMessage());
      return null;
    }

    KafkaSale kafkaSale = KafkaSale.TO.convert(sale);
    kafkaSale.set_dcbustype("sale");

    String value = objectMapper.writeValueAsString(kafkaSale);
    return value;
  }

}
