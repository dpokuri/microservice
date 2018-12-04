package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.fields.CampaignFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.CAMPAIGN)
public class Campaign extends BaseMongo {

  @Field(value = CampaignFields.NAME)
  private String name;

  @Field(value = CampaignFields.CODE)
  private String code;

  @Field(value = CampaignFields.PROMO_CODE_ADJUSTMENT)
  private String promoCodeAdjustmentId;

  @Field(value = CampaignFields.CAMPAIGN_PERIODS)
  private List<CampaignPeriod> campaignPeriods;

  @Field(value = CampaignFields.CAMPAIGN_STATUS)
  private CampaignStatus campaignStatus;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<CampaignPeriod> getCampaignPeriods() {
    return campaignPeriods;
  }

  public void setCampaignPeriods(
      List<CampaignPeriod> campaignPeriods) {
    this.campaignPeriods = campaignPeriods;
  }

  public String getPromoCodeAdjustmentId() {
    return promoCodeAdjustmentId;
  }

  public void setPromoCodeAdjustmentId(String promoCodeAdjustmentId) {
    this.promoCodeAdjustmentId = promoCodeAdjustmentId;
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
    return "Campaign{" +
        "name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", promoCodeAdjustmentId='" + promoCodeAdjustmentId + '\'' +
        ", campaignPeriods=" + campaignPeriods +
        ", campaignStatus=" + campaignStatus +
        '}' + super.toString();
  }
}
