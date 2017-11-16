package com.qianfan123.dpos.data.dao.sale;

import org.springframework.stereotype.Component;

import com.qianfan123.dpos.data.dao.AbstractShopQueryBatchable;

@Component
public class SaleShopQueryBatchableImpl extends AbstractShopQueryBatchable {

  @Override
  public String getTableName() {
    return "Sale";
  }

}
