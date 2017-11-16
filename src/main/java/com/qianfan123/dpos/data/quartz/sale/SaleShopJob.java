package com.qianfan123.dpos.data.quartz.sale;

import org.quartz.Job;

import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.sale.SaleShopQueryBatchableImpl;
import com.qianfan123.dpos.data.quartz.AbstractShopJob;

public class SaleShopJob extends AbstractShopJob {

  @Override
  public Class<? extends QueryBatchable> getBatchableClass() {
    return SaleShopQueryBatchableImpl.class;
  }

  @Override
  public Class<? extends Job> getUuidJobClass() {
    return SaleUuidJob.class;
  }

}
