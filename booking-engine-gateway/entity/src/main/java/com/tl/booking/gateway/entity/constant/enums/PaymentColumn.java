package com.tl.booking.gateway.entity.constant.enums;

public enum PaymentColumn {
  ID("ID", "_id"),
  NAME("NAME", "name"),
  PAYMENT_ID("PAYMENT_ID", "paymentId");

  private String name;
  private String value;

  PaymentColumn(String name, String value) {
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
