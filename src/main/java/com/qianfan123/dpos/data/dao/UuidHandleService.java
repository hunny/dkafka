package com.qianfan123.dpos.data.dao;

import com.qianfan123.dpos.data.dao.AbstraceBatchQueryService.BatchHandler;

public interface UuidHandleService {

  void handle(BatchHandler handler, String dbName, String shop);
  
}
