package com.qianfan123.dpos.data.dao.sale;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService;
import com.qianfan123.dpos.data.dao.QueryBatchable;

@Component
public class SaleUuidService extends AbstraceBatchQueryService {

  private QueryBatchable saleUuidQueryBatchable;
  
  @PostConstruct
  public void init() {
    saleUuidQueryBatchable = this.getApplicationContext().getBean(SaleUuidQueryBatchableImpl.class);
  }
  
  public void handle(BatchHandler handler, String dbName, String shop) {
    if (null == handler) {
      return;
    }
    handleBy(handler, saleUuidQueryBatchable, dbName, shop);
  }

}
