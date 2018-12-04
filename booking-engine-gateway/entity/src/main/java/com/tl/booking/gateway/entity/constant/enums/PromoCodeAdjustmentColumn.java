package com.tl.booking.gateway.entity.constant.enums;

public enum PromoCodeAdjustmentColumn {
  ID("ID", "_id"),
  NAME("NAME", "name"),
  IS_PROMOCODE_COMBINE("IS_PROMOCODE_COMBINE", "isPromoCodeCombine"),
  MAX_DISCOUNT("MAX_DISCOUNT", "maxDiscount"),
  PROMOCODE_ADJUSTMENT_STATUS("PROMOCODE_ADJUSTMENT_STATUS", "promoCodeAdjustmentStatus");

  private String name;
  private String value;

  PromoCodeAdjustmentColumn(String name, String value) {
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
