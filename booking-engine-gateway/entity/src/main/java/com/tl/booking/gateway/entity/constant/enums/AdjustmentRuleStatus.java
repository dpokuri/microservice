package com.tl.booking.gateway.entity.constant.enums;

public enum AdjustmentRuleStatus {
  DRAFT("DRAFT", "Draft"),
  PENDING("PENDING", "Pending"),
  ACTIVE("ACTIVE", "Active"),
  UNACTIVE("UNACTIVE", "UnActive");

  private String code;
  private String description;

  AdjustmentRuleStatus(String code, String description) {
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
