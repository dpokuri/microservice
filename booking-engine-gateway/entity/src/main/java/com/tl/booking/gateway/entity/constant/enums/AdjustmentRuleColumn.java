package com.tl.booking.gateway.entity.constant.enums;

public enum AdjustmentRuleColumn {
  ID("ID", "_id"),
  AIRLINE("AIRLINE", "airline"),
  ORIGIN("ORIGIN", "origin"),
  DESTINATION("DESTINATION", "destination");

  private String name;
  private String value;

  AdjustmentRuleColumn(String name, String value) {
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
