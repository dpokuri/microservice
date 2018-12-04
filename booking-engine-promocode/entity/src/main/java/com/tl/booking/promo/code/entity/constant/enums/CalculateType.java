package com.tl.booking.promo.code.entity.constant.enums;

public enum CalculateType {
  ORDER_DETAIL("ORDER_DETAIL", "ORDER_DETAIL"),
  ORDER("ORDER", "ORDER");


  private String name;
  private String value;

  CalculateType(String name, String value) {
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
