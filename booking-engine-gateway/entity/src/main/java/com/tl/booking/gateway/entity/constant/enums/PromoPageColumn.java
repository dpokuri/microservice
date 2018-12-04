package com.tl.booking.gateway.entity.constant.enums;

public enum  PromoPageColumn {

  ID("ID", "_id"),
  START_DATE("START_DATE", "startDate"),
  END_DATE("END_DATE", "endDate"),
  PRECEDENCE("PRECEDENCE", "precedence"),
  STATUS("STATUS", "status");

  private String name;
  private String value;

  PromoPageColumn(String name, String value)
  {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

}
