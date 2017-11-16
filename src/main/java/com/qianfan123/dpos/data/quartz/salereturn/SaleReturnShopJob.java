package com.qianfan123.dpos.data.quartz.salereturn;

import org.quartz.Job;

import com.qianfan123.dpos.data.dao.QueryBatchable;
import com.qianfan123.dpos.data.dao.salereturn.SaleReturnShopQueryBatchableImpl;
import com.qianfan123.dpos.data.quartz.AbstractShopJob;

public class SaleReturnShopJob extends AbstractShopJob {

  @Override
  public Class<? extends QueryBatchable> getBatchableClass() {
    return SaleReturnShopQueryBatchableImpl.class;
  }

  @Override
  public Class<? extends Job> getUuidJobClass() {
    return SaleReturnUuidJob.class;
  }

}
