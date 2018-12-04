package com.tl.booking.promo.code.entity.constant.enums;

public enum PromoCodeUsageLogColumn {
  ID("ID", "id"),
  CODE("CODE", "code");

  private String name;
  private String value;

  PromoCodeUsageLogColumn(String name, String value) {
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
