package com.yoryor.core.msg;


import java.util.Date;

public class Message {
  private final String msg;
  private final String producer;
  private final Date time;

  public Message(String msg, String producer) {
    this.msg = msg;
    this.producer = producer;
    time = new Date();
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
    return String.format("%s:%s:%s", producer, msg, time);
  }
}
