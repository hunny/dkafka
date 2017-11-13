package com.qianfan123.dpos.data.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hd123.dpos.api.sale.Sale;
import com.hd123.dpos.api.sale.SaleService;
import com.hd123.rumba.commons.biz.entity.EntityNotFoundException;
import com.hd123.rumba.commons.lang.Assert;
import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.common.RestResp;

@RestController()
@RequestMapping(value = "/sale", //
    produces = {
        MediaType.APPLICATION_JSON_UTF8_VALUE })
public class SaleController {

  @Autowired
  private SaleService saleService;

  @RequestMapping(value = {
      "/get/{shop}" }, method = RequestMethod.GET)
  public RestResp<Sale> get(@PathVariable String shop, //
      @RequestParam("uuid") String uuid) throws DkafkaException {
    Assert.notNull(shop);
    Assert.notNull(uuid);

    try {
      Sale sale = saleService.get(shop, uuid, Sale.ALL_PARTS);
      if (null == sale) {
        throw new DkafkaException("根据门店[{0}]和UUID[{1}]查询Sale无记录", shop, uuid);
      }
      return new RestResp<Sale>(sale);
    } catch (EntityNotFoundException | DkafkaException e) {
      return new RestResp(false, e.getMessage());
    }

  }

}
