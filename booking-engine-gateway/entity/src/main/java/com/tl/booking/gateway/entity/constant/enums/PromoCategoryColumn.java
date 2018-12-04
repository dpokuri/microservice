package com.tl.booking.gateway.entity.constant.enums;

public enum PromoCategoryColumn {

  ID("ID", "_id"),
  CATEGORY("CATEGORY", "category"),
  GROUP("GROUP", "group"),
  PARENT_CATEGORY("PARENT_CATEGORY", "parentCategory"),
  PRECEDENCE("PRECEDENCE", "precedence");

  private String name;
  private String value;

  PromoCategoryColumn(String name, String value)
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
