package com.qianfan123.dpos.data.config.date;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

public class DateDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectCodec codec = jp.getCodec();
    TextNode node = (TextNode) codec.readTree(jp);
    String dateString = node.textValue();
    try {
      return DateUtils.parseDate(dateString, new String[] {
          "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
    } catch (ParseException e) {
      try {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", //
            Locale.CHINA);// 输入的被转化的时间格式2017-11-06T06:36:28.103Z
        return dff.parse(dateString);
      } catch (ParseException e1) {
      }
      throw new IOException(e);
    }
  }

}
