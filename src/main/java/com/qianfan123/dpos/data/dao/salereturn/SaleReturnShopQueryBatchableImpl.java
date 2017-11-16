package com.qianfan123.dpos.data.dao.salereturn;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstractShopQueryBatchable;

@Component
public class SaleReturnShopQueryBatchableImpl extends AbstractShopQueryBatchable {

  @Override
  public String getTableName() {
    return "SaleReturn";
  }

}
