package com.qianfan123.dpos.data.service.sale;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd123.dpos.api.sale.Sale;
import com.hd123.dpos.api.sale.SaleService;
import com.hd123.rumba.commons.biz.entity.EntityNotFoundException;
import com.qianfan123.dpos.data.common.DkafkaException;

@Component
public class KafkaSaleService {

  @Autowired
  private SaleService saleService;

  @Autowired
  private ObjectMapper objectMapper;

  public String getAsString(String shop, String uuid) throws DkafkaException, IOException {
    Sale sale = null;
    try {
      sale = saleService.get(shop, uuid, Sale.ALL_PARTS);
      if (null == sale) {
        throw new DkafkaException("根据门店[{0}]和UUID[{1}]查询Sale无记录", shop, uuid);
      }
    } catch (EntityNotFoundException e) {
      throw new DkafkaException("根据门店[{0}]和UUID[{1}]查询Sale无记录", shop, uuid);
    }

    String value = objectMapper.writeValueAsString(sale);
    KafkaSale kafkaSale = objectMapper.readValue(value, KafkaSale.class);
    kafkaSale.set_dcbustype("sale");

    value = objectMapper.writeValueAsString(kafkaSale);
    return value;
  }

}
