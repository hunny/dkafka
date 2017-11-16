package com.qianfan123.dpos.data.dao.sale;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import com.hd123.rumba.commons.jdbc.sql.SelectBuilder;
import com.hd123.rumba.commons.jdbc.sql.SelectStatement;
import com.qianfan123.dpos.data.dao.QueryBatchable;

@Component
public class SaleShopQueryBatchableImpl implements QueryBatchable {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<String> listBy(int offset, int limit, String... strs) {

    if (strs.length != 1) {
      throw new IllegalArgumentException(//
          MessageFormat.format("只需要一个dbName参数,{0}", Arrays.asList(strs)));
    }

    SelectStatement select = new SelectBuilder() //
        .distinct().select("shop") //
        .from(strs[0], "Sale", "_Sale")//
        .limit(offset, limit)//
        .build();

    return jdbcTemplate.query(select, new SingleColumnRowMapper(String.class));
  }

}
