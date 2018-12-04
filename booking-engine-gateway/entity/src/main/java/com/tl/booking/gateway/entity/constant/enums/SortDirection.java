package com.tl.booking.gateway.entity.constant.enums;

public enum SortDirection {
  ASC("ASC", "ASC"),
  DESC("DESC", "DESC");

  private String name;
  private String value;

  SortDirection(String name, String value) {
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
