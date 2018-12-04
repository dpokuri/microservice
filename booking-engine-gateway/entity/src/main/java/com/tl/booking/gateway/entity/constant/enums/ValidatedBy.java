package com.tl.booking.gateway.entity.constant.enums;

public enum ValidatedBy {
  USERNAME("USERNAME", "USERNAME"),
  CARD_NUMBER("CARD_NUMBER", "CARD_NUMBER");


  private String name;
  private String value;

  ValidatedBy(String name, String value) {
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
