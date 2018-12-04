package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PROMO_CODE)
public class PromoCode extends BaseMongo {

  @Field(value = PromoCodeFields.CODE)
  private String code;

  @Field(value = PromoCodeFields.CAMPAIGN_ID)
  private String campaignId;

  @Field(value = PromoCodeFields.MAX_QTY)
  private Integer maxQty;

  @Field(value = PromoCodeFields.PROMO_CODE_STATUS)
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
    return "PromoCode{" +
        "code='" + code + '\'' +
        ", campaignId='" + campaignId + '\'' +
        ", maxQty=" + maxQty +
        ", promoCodeStatus=" + promoCodeStatus +
        "} " + super.toString();
  }
}
