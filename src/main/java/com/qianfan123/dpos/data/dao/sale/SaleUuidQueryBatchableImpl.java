package com.qianfan123.dpos.data.dao.sale;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import com.hd123.rumba.commons.jdbc.sql.Predicates;
import com.hd123.rumba.commons.jdbc.sql.SelectBuilder;
import com.hd123.rumba.commons.jdbc.sql.SelectStatement;
import com.qianfan123.dpos.data.dao.QueryBatchable;

@Component
public class SaleUuidQueryBatchableImpl implements QueryBatchable {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<String> listBy(int offset, int limit, String... strs) {

    if (strs.length != 2) {
      throw new IllegalArgumentException(//
          MessageFormat.format("需要传递二个参数dbName和shopId参数,{0}", Arrays.asList(strs)));
    }

    SelectStatement select = new SelectBuilder() //
        .select("uuid") //
        .from(strs[0], "Sale", "_Sale") //
        .where(Predicates.equals("_Sale", "shop", strs[1])) //
        .limit(offset, limit) //
        .build();

    return jdbcTemplate.query(select, new SingleColumnRowMapper(String.class));
  }

}
