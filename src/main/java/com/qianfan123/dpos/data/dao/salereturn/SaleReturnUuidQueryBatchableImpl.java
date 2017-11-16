package com.qianfan123.dpos.data.dao.salereturn;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstractUuidQueryBatchable;

@Component
public class SaleReturnUuidQueryBatchableImpl extends AbstractUuidQueryBatchable {

  @Override
  public String getTableName() {
    return "SaleReturn";
  }

}
