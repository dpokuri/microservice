package com.tl.booking.promo.code.entity;

import java.util.Date;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeUsageParameter {
  String promoCodeId;
  Date startDate;
  Date endDate;
  String storeId;
  String channelId;
  String username;
  Integer maxQty;
  Integer increment;

  public String getPromoCodeId() {
    return promoCodeId;
  }

  public void setPromoCodeId(String promoCodeId) {
    this.promoCodeId = promoCodeId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getMaxQty() {
    return maxQty;
  }

  public void setMaxQty(Integer maxQty) {
    this.maxQty = maxQty;
  }

  public Integer getIncrement() {
    return increment;
  }

  public void setIncrement(Integer increment) {
    this.increment = increment;
  }

  @Override
  public String toString() {
    return "PromoCodeUsageParameter{" +
        "promoCodeId='" + promoCodeId + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", storeId='" + storeId + '\'' +
        ", channelId='" + channelId + '\'' +
        ", username='" + username + '\'' +
        ", maxQty=" + maxQty +
        ", increment=" + increment +
        '}';
  }
}
