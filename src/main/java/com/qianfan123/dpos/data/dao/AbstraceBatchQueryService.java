package com.qianfan123.dpos.data.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstraceBatchQueryService implements ApplicationContextAware {

  private ApplicationContext applicationContext;
  
  @Value("${dkafka-dpos-service.shop.query.pagesize:1000}")
  private Integer limit;

  public void handleBy(Handler handler, Inquirable inquirable, String... params) {
    int offset = 0;
    do {
      List<String> elems = list(offset, limit, inquirable, params);
      offset += limit;
      if (null == elems || elems.isEmpty()) {
        break;
      }
      handler.handle(elems, params);
    } while (true);
  }

  public List<String> list(int offset, Integer limit, Inquirable inquirable, String... params) {
    List<String> result = new ArrayList<>();
    List<String> elems = inquirable.listBy(offset, limit, params);
    if (null != elems && !elems.isEmpty()) {
      result.addAll(elems);
    }
    if (result.isEmpty()) {
      return Collections.emptyList();
    }
    Set<String> set = new HashSet<>(result);
    return Arrays.asList(set.toArray(new String[] {}));
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public static interface Handler {
    void handle(List<String> elems, String... strings);
  }

}
