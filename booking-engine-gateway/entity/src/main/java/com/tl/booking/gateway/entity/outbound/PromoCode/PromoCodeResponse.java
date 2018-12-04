package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.gateway.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeResponse extends BaseMongoResponse {

  private String id;
  private String code;
  private String campaignId;
  private Integer maxQty;
  private PromoCodeStatus promoCodeStatus;
  private List<String> allowedActions;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public Integer getMaxQty() {
    return maxQty;
  }

  public void setMaxQty(Integer maxQty) {
    this.maxQty = maxQty;
  }

  public PromoCodeStatus getPromoCodeStatus() {
    return promoCodeStatus;
  }

  public void setPromoCodeStatus(
      PromoCodeStatus promoCodeStatus) {
    this.promoCodeStatus = promoCodeStatus;
  }

  public List<String> getAllowedActions() {
    return allowedActions;
  }

  public void setAllowedActions(List<String> allowedActions) {
    this.allowedActions = allowedActions;
  }

  @Override
  public String toString() {
    return "PromoCodeResponse{" +
        "id='" + id + '\'' +
        ", code='" + code + '\'' +
        ", campaignId='" + campaignId + '\'' +
        ", maxQty=" + maxQty +
        ", promoCodeStatus=" + promoCodeStatus +
        ", allowedActions=" + allowedActions +
        '}' + super.toString();
  }
}
