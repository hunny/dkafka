package com.qianfan123.dpos.data.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hd123.dpos.api.sale.Sale;
import com.hd123.dpos.api.salereturn.SaleReturn;
import com.hd123.rumba.commons.biz.entity.Nsid;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    objectMapper.addMixIn(Sale.class, FetchPartsMixIn.class);
    objectMapper.addMixIn(SaleReturn.class, FetchPartsMixIn.class);
    
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Nsid.class, new JsonDeserializer<Nsid>() {
      @Override
      public Nsid deserialize(JsonParser jp, DeserializationContext ctxt)
          throws IOException, JsonProcessingException {
        JsonNode node = jp.readValueAsTree();
        return new Nsid(node.get("namespace").asText(), node.get("id").asText());
      }
    });
    module.addSerializer(Date.class, new DateSerializer());
    module.addDeserializer(Date.class, new DateDeserializer());
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    objectMapper.registerModule(module);
    
    return objectMapper;
  }
  
  /**
   * 在反序列化时，忽略fetchParts 属性， 因为 fetchParts 对应两个set方法，反序列化会冲突。
   */
  static abstract class FetchPartsMixIn {
    @JsonIgnore
    abstract Set<String> getFetchParts();
  }
  
}
