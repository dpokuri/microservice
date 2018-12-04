package com.tl.booking.gateway.entity.constant.enums;

public enum PromoCodeStatus {
  ACTIVE("ACTIVE", "Active promo code"),
  INACTIVE("INACTIVE", "Inactive promo code");

  private String code;
  private String description;

  PromoCodeStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
