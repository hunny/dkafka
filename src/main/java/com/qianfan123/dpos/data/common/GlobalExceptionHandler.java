package com.qianfan123.dpos.data.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(value = DkafkaException.class)
  public Map<String, Object> handleException(DkafkaException e) {
    logger.error(e.getMessage());
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("message", e.getMessage());
    return result;
  }

}
