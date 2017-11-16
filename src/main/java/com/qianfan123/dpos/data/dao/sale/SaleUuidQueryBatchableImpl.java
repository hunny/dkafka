package com.qianfan123.dpos.data.dao.sale;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstractUuidQueryBatchable;

@Component
public class SaleUuidQueryBatchableImpl extends AbstractUuidQueryBatchable {

  @Override
  public String getTableName() {
    return "Sale";
  }

}
