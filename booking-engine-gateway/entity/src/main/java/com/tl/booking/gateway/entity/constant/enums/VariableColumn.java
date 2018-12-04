package com.tl.booking.gateway.entity.constant.enums;

public enum VariableColumn {
  ID("ID", "_id"),
  PARAM("PARAM", "param"),
  INPUT_TYPE("INPUT_TYPE", "inputType");

  private String name;
  private String value;

  VariableColumn(String name, String value) {
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
