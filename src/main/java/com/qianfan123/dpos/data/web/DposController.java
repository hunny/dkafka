package com.qianfan123.dpos.data.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qianfan123.dpos.data.common.DkafkaException;
import com.qianfan123.dpos.data.common.RestResp;
import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService.BatchHandler;
import com.qianfan123.dpos.data.dao.sale.SaleShopQueryBatchableImpl;
import com.qianfan123.dpos.data.dao.DbCommonDao;
import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.ShopService;

@RestController
@RequestMapping(value = "/dpos", //
    produces = {
        MediaType.APPLICATION_JSON_UTF8_VALUE })
public class DposController implements ApplicationContextAware {

  @Autowired
  private DbCommonDao dbCommonDao;

  @Autowired
  private ShopService shopService;

  private ApplicationContext applicationContext;

  private QueryBatchable saleShopBatchable;

  @PostConstruct
  public void init() {
    saleShopBatchable = this.applicationContext.getBean(SaleShopQueryBatchableImpl.class);
  }

  @RequestMapping(value = {
      "/all/db" }, method = RequestMethod.GET)
  public RestResp<List<String>> getAllDb() throws DkafkaException {

    List<String> dbs = dbCommonDao.getShopDbNames();
    return new RestResp<List<String>>(dbs);
  }

  @RequestMapping(value = {
      "/{db}/sale/shops" }, //
      method = RequestMethod.GET)
  public RestResp<List<String>> getAllShops(@PathVariable String db) throws DkafkaException {
    final List<String> list = new ArrayList<>();
    shopService.handleBy(new BatchHandler() {
      @Override
      public void handle(List<String> shops, String...strings) {
        list.addAll(shops);
      }
    }, saleShopBatchable, db);
    return new RestResp<List<String>>(list);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
