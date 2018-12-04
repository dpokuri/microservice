package com.tl.booking.promo.code.entity.constant.enums;

public enum IgnoreValidation {
  QUANTITY("QUANTITY", "QUANTITY"),
  PAYMENT_METHOD("PAYMENT_METHOD", "PAYMENT_METHOD"),
  PAYMENT_METHOD_QUANTITY("PAYMENT_METHOD_QUANTITY", "PAYMENT_METHOD_QUANTITY"),
  NONE("NONE", "NONE");

  private String name;
  private String value;

  IgnoreValidation(String name, String value) {
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
