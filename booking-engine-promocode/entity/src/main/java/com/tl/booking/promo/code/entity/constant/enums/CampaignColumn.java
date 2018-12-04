package com.tl.booking.promo.code.entity.constant.enums;

public enum CampaignColumn {
  ID("ID", "_id"),
  NAME("NAME", "name"),
  CAMPAIGN_STATUS("CAMPAIGN_STATUS", "campaignStatus");

  private String name;
  private String value;

  CampaignColumn(String name, String value) {
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
