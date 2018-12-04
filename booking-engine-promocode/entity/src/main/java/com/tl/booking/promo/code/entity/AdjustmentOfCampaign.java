package com.tl.booking.promo.code.entity;

import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class AdjustmentOfCampaign {
  private String campaignId;
  private List<PromoCodeAdjustment> promoCodeAdjustments;

  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public List<PromoCodeAdjustment> getPromoCodeAdjustments() {
    return promoCodeAdjustments;
  }

  public void setPromoCodeAdjustments(
      List<PromoCodeAdjustment> promoCodeAdjustments) {
    this.promoCodeAdjustments = promoCodeAdjustments;
  }

  @Override
  public String toString() {
    return "AdjustmentOfCampaign{" +
        "campaignId='" + campaignId + '\'' +
        ", promoCodeAdjustments=" + promoCodeAdjustments +
        '}' + super.toString();
  }
}
