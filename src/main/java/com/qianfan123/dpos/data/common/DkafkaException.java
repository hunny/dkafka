package com.qianfan123.dpos.data.common;

import java.text.MessageFormat;

public class DkafkaException extends Exception {

  private static final long serialVersionUID = 5940870272806603861L;

  public DkafkaException() {
    // Do Nothing
  }

  public DkafkaException(String pattern, Object... arguments) {
    super(MessageFormat.format(pattern, arguments));
  }

  public DkafkaException(Throwable t) {
    super(t);
  }

  public DkafkaException(Throwable t, String pattern, Object... arguments) {
    super(MessageFormat.format(pattern, arguments), t);
  }
}
