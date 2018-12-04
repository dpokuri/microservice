package com.tl.booking.gateway.entity.constant.enums;

public enum CampaignStatus {
  DRAFT("DRAFT", "After adjustment creation, need to be submitted for approval"),
  PENDING("PENDING", "Waiting for approval after draft being submitted"),
  ACTIVE("ACTIVE", "Adjustment active already"),
  INACTIVE("INACTIVE", "DIsable promo code adjustment");

  private String code;
  private String description;

  CampaignStatus(String code, String description) {
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
