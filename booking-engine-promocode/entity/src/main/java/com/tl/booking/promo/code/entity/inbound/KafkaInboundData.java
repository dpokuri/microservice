package com.tl.booking.promo.code.entity.inbound;

import java.util.List;
import java.util.Map;

public class KafkaInboundData {

  List<Map<String, String>> data;
  String timestamps;
  String action;

  public List<Map<String, String>> getData() {
    return data;
  }

  public void setData(List<Map<String, String>> data) {
    this.data = data;
  }

  public String getTimestamps() {
    return timestamps;
  }

  public void setTimestamps(String timestamps) {
    this.timestamps = timestamps;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  @Override
  public String toString() {
    return "KafkaInboundData{" +
        "data=" + data +
        ", timestamps='" + timestamps + '\'' +
        ", action='" + action + '\'' +
        '}';
  }
}
