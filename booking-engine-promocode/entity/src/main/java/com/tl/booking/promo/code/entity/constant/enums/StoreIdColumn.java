package com.tl.booking.promo.code.entity.constant.enums;

public enum StoreIdColumn {
  ID("ID", "_id"),
  NAME("NAME", "name");

  private String name;
  private String value;

  StoreIdColumn(String name, String value) {
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
