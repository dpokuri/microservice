package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeUsageFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import java.util.Date;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PROMO_CODE_USAGE)
public class PromoCodeUsage extends BaseMongo {

  @Field(value = PromoCodeUsageFields.PROMO_CODE_ID)
  private String promoCodeId;

  @Field(value = PromoCodeUsageFields.PROMO_CODE)
  private String promoCode;

  @Field(value = PromoCodeUsageFields.CARD_NUMBER)
  private String cardNumber;

  @Field(value = PromoCodeUsageFields.START_DATE)
  private Date startDate;

  @Field(value = PromoCodeUsageFields.END_DATE)
  private Date endDate;

  @Field(value = PromoCodeUsageFields.USAGE_DATE)
  private Date usageDate;

  @Field(value = PromoCodeUsageFields.USAGE_COUNT)
  private Integer usageCount;

  public String getPromoCodeId() {
    return promoCodeId;
  }

  public void setPromoCodeId(String promoCodeId) {
    this.promoCodeId = promoCodeId;
  }

  public String getPromoCode() {
    return promoCode;
  }

  public void setPromoCode(String promoCode) {
    this.promoCode = promoCode;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
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

  public Date getUsageDate() {
    return usageDate;
  }

  public void setUsageDate(Date usageDate) {
    this.usageDate = usageDate;
  }

  public Integer getUsageCount() {
    return usageCount;
  }

  public void setUsageCount(Integer usageCount) {
    this.usageCount = usageCount;
  }

  @Override
  public String toString() {
    return "PromoCodeUsage{" +
        "promoCodeId='" + promoCodeId + '\'' +
        ", promoCode='" + promoCode + '\'' +
        ", cardNumber='" + cardNumber + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", usageDate=" + usageDate +
        ", usageCount=" + usageCount +
        '}';
  }
}
