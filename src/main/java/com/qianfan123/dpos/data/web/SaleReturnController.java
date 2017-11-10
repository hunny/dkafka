package com.qianfan123.dpos.data.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hd123.dpos.api.sale.Sale;
import com.hd123.dpos.api.salereturn.SaleReturn;
import com.hd123.dpos.api.salereturn.SaleReturnService;
import com.hd123.rumba.commons.biz.entity.EntityNotFoundException;
import com.hd123.rumba.commons.lang.Assert;
import com.qianfan123.dpos.data.common.DkafkaException;

@RestController()
@RequestMapping(value = "/sale-return", //
    produces = {
        MediaType.APPLICATION_JSON_UTF8_VALUE })
public class SaleReturnController {

  @Autowired
  private SaleReturnService saleReturnService;

  @RequestMapping(value = {
      "/get/{shop}" }, method = RequestMethod.GET)
  public SaleReturn get(@PathVariable String shop, //
      @RequestParam("uuid") String uuid) throws DkafkaException {
    Assert.notNull(shop);
    Assert.notNull(uuid);
    
    try {
      SaleReturn saleReturn = saleReturnService.get(shop, uuid, Sale.ALL_PARTS);
      if (null == saleReturn) {
        throw new DkafkaException("根据门店[{0}]和UUID[{1}]查询无记录", shop, uuid);
      }
      return saleReturn;
    } catch (EntityNotFoundException e) {
      throw new DkafkaException(e);
    }
  }

}
