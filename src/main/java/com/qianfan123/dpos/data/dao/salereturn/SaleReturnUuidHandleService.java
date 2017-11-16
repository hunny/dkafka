package com.qianfan123.dpos.data.dao.salereturn;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService;
import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.UuidHandleService;

@Component
public class SaleReturnUuidHandleService extends AbstraceBatchQueryService implements UuidHandleService {

  private QueryBatchable saleReturnUuidQueryBatchable;

  @PostConstruct
  public void init() {
    saleReturnUuidQueryBatchable = this.getApplicationContext()
        .getBean(SaleReturnUuidQueryBatchableImpl.class);
  }

  @Override
  public void handle(BatchHandler handler, String dbName, String shop) {
    if (null == handler) {
      return;
    }
    handleBy(handler, saleReturnUuidQueryBatchable, dbName, shop);
  }

}
