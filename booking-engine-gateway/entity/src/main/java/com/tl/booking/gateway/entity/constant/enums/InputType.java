package com.tl.booking.gateway.entity.constant.enums;

public enum InputType {
  TEXT("TEXT", "TEXT"),
  DROPDOWN("DROPDOWN", "DROPDOWN"),
  AUTOCOMPLETE("AUTOCOMPLETE", "AUTOCOMPLETE");

  private String name;
  private String value;

  InputType(String name, String value) {
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
