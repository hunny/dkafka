package com.qianfan123.dpos.data.dao;

import java.util.List;

public interface QueryBatchable {

  List<String> listBy(int offset, int limit, String... strs);
  
}
