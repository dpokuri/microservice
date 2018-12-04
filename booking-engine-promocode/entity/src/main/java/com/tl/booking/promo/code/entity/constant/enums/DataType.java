package com.tl.booking.promo.code.entity.constant.enums;

public enum DataType {
  STRING("STRING", "STRING"),
  DATE("DATE", "DATE"),
  INTEGER("INTEGER", "INTEGER"),
  DOUBLE("DOUBLE", "DOUBLE");

  private String name;
  private String value;

  DataType(String name, String value) {
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
