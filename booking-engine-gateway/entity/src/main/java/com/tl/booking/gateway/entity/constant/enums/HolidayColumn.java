package com.tl.booking.gateway.entity.constant.enums;

public enum HolidayColumn {
  ID("ID", "_id"),
  DATE("DATE", "date");

  private String name;
  private String value;

  HolidayColumn(String name, String value)
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
