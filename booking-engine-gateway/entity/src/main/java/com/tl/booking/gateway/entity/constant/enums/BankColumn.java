package com.tl.booking.gateway.entity.constant.enums;

public enum BankColumn {
  ID("ID", "_id"),
  NAME("NAME", "name");

  private String name;
  private String value;

  BankColumn(String name, String value) {
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
