package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import java.util.List;

public class CampaignResponse extends BaseMongoResponse {

  private String name;
  private String code;
  private String promoCodeAdjustmentId;
  private List<CampaignPeriodResponse> campaignPeriods;
  private CampaignStatus campaignStatus;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getPromoCodeAdjustmentId() {
    return promoCodeAdjustmentId;
  }

  public void setPromoCodeAdjustmentId(String promoCodeAdjustmentId) {
    this.promoCodeAdjustmentId = promoCodeAdjustmentId;
  }

  public List<CampaignPeriodResponse> getCampaignPeriods() {
    return campaignPeriods;
  }

  public void setCampaignPeriods(
      List<CampaignPeriodResponse> campaignPeriods) {
    this.campaignPeriods = campaignPeriods;
  }

  public CampaignStatus getCampaignStatus() {
    return campaignStatus;
  }

  public void setCampaignStatus(
      CampaignStatus campaignStatus) {
    this.campaignStatus = campaignStatus;
  }

  @Override
  public String toString() {
    return "CampaignResponse{" +
        "name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", promoCodeAdjustmentId='" + promoCodeAdjustmentId + '\'' +
        ", campaignPeriods=" + campaignPeriods +
        ", campaignStatus=" + campaignStatus +
        '}';
  }
}
