package com.tl.booking.gateway.entity.constant.enums;

public enum PromoCodeColumn {
  ID("ID", "id"),
  CODE("CODE", "code"),
  CAMPAIGN_ID("CAMPAIGN_ID", "campaignId");

  private String name;
  private String value;

  PromoCodeColumn(String name, String value) {
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
