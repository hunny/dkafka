package com.qianfan123.dpos.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShopService extends AbstraceBatchQueryService {

  public static final String DB_NAME = "dbName";
  public static final String SHOP_ID = "shopId";
  
  @Autowired
  private DbCommonDao dbCommonDao;

  /**
   * @param handler
   * @param shopListable, 产生shops依赖查询
   */
  public void handle(Handler handler, Inquirable shopListable) {
    if (null == handler) {
      return;
    }
    List<String> dbs = dbCommonDao.getShopDbNames();
    for (final String db : dbs) {
      handleBy(handler, shopListable, db);
    }
  }

}
