package com.qianfan123.dpos.data.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestResp<V> implements Serializable {

  private static final long serialVersionUID = 9046674796705445319L;

  private boolean success = false;
  private V data = null;
  private final List<String> messages = new ArrayList<String>();

  public RestResp() {
    // Do Nothing
  }

  public RestResp(boolean success) {
    this.success = success;
  }

  public RestResp(V data) {
    this.success = true;
    this.data = data;
  }

  public RestResp(boolean success, String message) {
    this.success = success;
    this.messages.add(message);
  }

  public RestResp(boolean success, List<String> messages) {
    this.success = success;
    if (null != messages) {
      this.messages.addAll(messages);
    }
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public V getData() {
    return data;
  }

  public void setData(V data) {
    this.data = data;
  }

  public String getMessage() {
    if (this.messages.isEmpty()) {
      return null;
    }
    return this.messages.get(0);
  }

  public void setMessage(String message) {
    if (this.messages.isEmpty()) {
      this.messages.add(message);
    } else {
      this.messages.set(0, message);
    }
  }

  public List<String> getMessages() {
    return messages;
  }

  public void setMessages(List<String> messages) {
    this.messages.clear();
    if (null != messages) {
      this.messages.addAll(messages);
    }
  }

}
