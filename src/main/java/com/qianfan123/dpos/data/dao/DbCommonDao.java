/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2017，所有权利保留。
 * 
 * 项目名：	mbr-service
 * 文件名：	DbCommonDao.java
 * 模块说明：	
 * 修改历史：
 * 2017年6月23日 - huzexiong - 创建。
 */
package com.qianfan123.dpos.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import com.hd123.rumba.commons.jdbc.sql.Predicates;
import com.hd123.rumba.commons.jdbc.sql.SelectBuilder;
import com.hd123.rumba.commons.jdbc.sql.SelectStatement;

/**
 * @author huzexiong
 *
 */
@Component
@CacheConfig(cacheNames = "dbnames")
public class DbCommonDao {

  private final JdbcTemplate jdbcTemplate;
  
  @Value(value = "${dkafka-dpos-service.shopdb.prefix:db}")
  private String dbPrefix;

  @Autowired
  public DbCommonDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * @return 资源栈内的门店数据库名称
   */
  @Cacheable
  public List<String> getShopDbNames() {
    final SelectStatement select = new SelectBuilder() //
        .select("schema_name") //
        .from("information_schema.schemata") //
        .where(Predicates.startsWith("schema_name", dbPrefix)) //
        .build();
    List<String> list = jdbcTemplate.query(select, new SingleColumnRowMapper<>(String.class));

    return list;
  }
}
