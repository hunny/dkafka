package com.qianfan123.dpos.data.dao;

import java.util.List;

public interface Inquirable {

  List<String> listBy(int offset, int limit, String... strs);
  
}
