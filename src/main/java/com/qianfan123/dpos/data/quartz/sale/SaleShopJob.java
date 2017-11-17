package com.qianfan123.dpos.data.quartz.sale;

import org.quartz.DisallowConcurrentExecution;

import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.sale.SaleShopQueryBatchableImpl;
import com.qianfan123.dpos.data.quartz.AbstractShopJob;
import com.qianfan123.dpos.data.quartz.AbstractUuidSenderService;

@DisallowConcurrentExecution
public class SaleShopJob extends AbstractShopJob {

  @Override
  public Class<? extends QueryBatchable> getBatchableClass() {
    return SaleShopQueryBatchableImpl.class;
  }

  @Override
  public Class<? extends AbstractUuidSenderService> getUuidSenderClass() {
    return SaleUuidSenderService.class;
  }

//  @Override
//  public Class<? extends Job> getUuidJobClass() {
//    return SaleUuidSenderJob.class;
//  }

}
