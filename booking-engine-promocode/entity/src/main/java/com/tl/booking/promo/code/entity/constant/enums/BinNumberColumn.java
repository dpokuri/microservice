package com.tl.booking.promo.code.entity.constant.enums;

public enum BinNumberColumn {
  ID("ID", "_id"),
  BIN_NUMBER("BIN_NUMBER", "binNumber");

  private String name;
  private String value;

  BinNumberColumn(String name, String value) {
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
