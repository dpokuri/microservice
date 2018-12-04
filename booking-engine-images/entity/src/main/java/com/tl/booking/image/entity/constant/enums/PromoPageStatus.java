package com.tl.booking.image.entity.constant.enums;

public enum PromoPageStatus {
  DRAFT("DRAFT", "After Promo Page creation, need to be submitted for approval"),
  PENDING("PENDING", "Waiting for approval after draft being submitted"),
  ACTIVE("ACTIVE", "Promo Page active already"),
  INACTIVE("INACTIVE", "DIsable Promo Page");

  private String code;
  private String description;

  PromoPageStatus(String code, String description) {
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
