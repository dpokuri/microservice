package com.tl.booking.promo.code.entity.constant.enums;

public enum PromoCodeType {
  GENERAL("GENERAL", "GENERAL"),
  QUALIFIED_PURCHASE("QUALIFIED_PURCHASE", "QUALIFIED_PURCHASE");


  private String code;
  private String name;

  PromoCodeType(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
