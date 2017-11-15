package com.qianfan123.dpos.data.dao.sale;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService;
import com.qianfan123.dpos.data.dao.Inquirable;

@Component
public class SaleUuidService extends AbstraceBatchQueryService {

  private Inquirable saleUuidInquirable;
  
  @PostConstruct
  public void init() {
    saleUuidInquirable = this.getApplicationContext().getBean(SaleUuidInquirableImpl.class);
  }
  
  public void handle(Handler handler, String dbName, String shop) {
    if (null == handler) {
      return;
    }
    handleBy(handler, saleUuidInquirable, dbName, shop);
  }

}
