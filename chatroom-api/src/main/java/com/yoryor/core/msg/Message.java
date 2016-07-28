package com.yoryor.core.msg;


public class Message {
  private final String msg;
  private final String producer;

  public Message(String msg, String producer) {
    this.msg = msg;
    this.producer = producer;
  }

  public String getMsg() {
    return msg;
  }

  public String getProducer() {
    return producer;
  }

  @Override
  public String toString() {
    return "Message [" +
           "msg=" + msg +
           ", producer=" + producer +
           "]";
  }

  public String formatString() {
    return String.format("%s:%s", producer, msg);
  }
}
