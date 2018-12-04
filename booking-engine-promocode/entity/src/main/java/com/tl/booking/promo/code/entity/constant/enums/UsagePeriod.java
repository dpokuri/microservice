package com.tl.booking.promo.code.entity.constant.enums;

public enum UsagePeriod {
  DAILY("DAILY", "DAILY"),
  PERIOD("PERIOD", "PERIOD");

  private String code;
  private String name;

  UsagePeriod(String code, String name) {
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
