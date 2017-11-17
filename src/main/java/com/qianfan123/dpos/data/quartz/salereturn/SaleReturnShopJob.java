package com.qianfan123.dpos.data.quartz.salereturn;

import org.quartz.DisallowConcurrentExecution;

import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.salereturn.SaleReturnShopQueryBatchableImpl;
import com.qianfan123.dpos.data.quartz.AbstractShopJob;
import com.qianfan123.dpos.data.quartz.AbstractUuidSenderService;

@DisallowConcurrentExecution
public class SaleReturnShopJob extends AbstractShopJob {

  @Override
  public Class<? extends QueryBatchable> getBatchableClass() {
    return SaleReturnShopQueryBatchableImpl.class;
  }

  @Override
  public Class<? extends AbstractUuidSenderService> getUuidSenderClass() {
    return SaleReturnUuidSenderService.class;
  }

//  @Override
//  public Class<? extends Job> getUuidJobClass() {
//    return SaleReturnUuidSenderJob.class;
//  }

}
