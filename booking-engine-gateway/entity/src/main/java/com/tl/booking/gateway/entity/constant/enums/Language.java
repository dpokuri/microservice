package com.tl.booking.gateway.entity.constant.enums;

public enum Language {
  ID("ID", "Indonesia"),
  EN("EN", "English");

  private final String code;
  private final String name;

  Language(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
