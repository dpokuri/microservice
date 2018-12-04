package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CampaignResponse extends BaseMongoResponse {

  private String id;
  private String name;
  private String code;
  private String promoCodeAdjustmentId;
  private List<CampaignPeriodResponse> campaignPeriods;
  private CampaignStatus campaignStatus;
  private List<String> allowedActions;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public List<String> getAllowedActions() {
    return allowedActions;
  }

  public void setAllowedActions(List<String> allowedActions) {
    this.allowedActions = allowedActions;
  }

  @Override
  public String toString() {
    return "CampaignResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", promoCodeAdjustmentId='" + promoCodeAdjustmentId + '\'' +
        ", campaignPeriods=" + campaignPeriods +
        ", campaignStatus=" + campaignStatus +
        ", allowedActions=" + allowedActions +
        '}' + super.toString();
  }
}
