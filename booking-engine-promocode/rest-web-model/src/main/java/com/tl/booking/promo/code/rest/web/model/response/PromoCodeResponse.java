package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeResponse extends BaseMongoResponse {

  private String code;
  private String campaignId;
  private Integer maxQty;
  private PromoCodeStatus promoCodeStatus;


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

  @Override
  public String toString() {
    return "PromoCodeResponse{" +
        "code='" + code + '\'' +
        ", campaignId='" + campaignId + '\'' +
        ", maxQty=" + maxQty +
        ", promoCodeStatus=" + promoCodeStatus +
        "} " + super.toString();
  }
}
