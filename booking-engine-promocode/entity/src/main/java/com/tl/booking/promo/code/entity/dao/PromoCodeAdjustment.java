package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.PaymentMethod;
import com.tl.booking.promo.code.entity.PromoCodeCost;
import com.tl.booking.promo.code.entity.PromoCodeDistribution;
import com.tl.booking.promo.code.entity.PromoCodeGroupRule;
import com.tl.booking.promo.code.entity.PromoCodePriceRange;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.enums.CalculateType;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeType;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeAdjustmentFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import java.util.List;
import java.util.Set;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PROMO_CODE_ADJUSTMENT)
public class PromoCodeAdjustment extends BaseMongo {

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_TYPE)
  private PromoCodeType promoCodeType;

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_COMBINE)
  private boolean isPromoCodeCombine;

  @Field(value = PromoCodeAdjustmentFields.CODE)
  private String code;

  @Field(value = PromoCodeAdjustmentFields.NAME)
  private String name;

  @Field(value = PromoCodeAdjustmentFields.DESCRIPTION)
  private String description;

  @Field(value = PromoCodeAdjustmentFields.USAGE_RULE)
  private List<UsageRule> usageRules;

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_DISTRIBUTION)
  private Set<PromoCodeDistribution> promoCodeDistributions;

  @Field(value = PromoCodeAdjustmentFields.MAX_DISCOUNT)
  private Double maxDiscount;

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_GROUP_RULES)
  private List<PromoCodeGroupRule> promoCodeGroupRules;

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_ADJUSTMENT_STATUS)
  private PromoCodeAdjustmentStatus promoCodeAdjustmentStatus = PromoCodeAdjustmentStatus.DRAFT;

  @Field(value = PromoCodeAdjustmentFields.CALCULATE_TYPE)
  private CalculateType calculateType;

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_PRICE_RANGES)
  private List<PromoCodePriceRange> promoCodePriceRanges;

  @Field(value = PromoCodeAdjustmentFields.PROMO_CODE_COST)
  private PromoCodeCost promoCodeCost;

  @Field(value = PromoCodeAdjustmentFields.PAYMENT_METHODS)
  private List<PaymentMethod> paymentMethods;

  @Field(value = PromoCodeAdjustmentFields.IS_VALID_ALL_ORDER_DETAILS)
  private Boolean isValidAllOrderDetails;

  public PromoCodeType getPromoCodeType() {
    return promoCodeType;
  }

  public void setPromoCodeType(PromoCodeType promoCodeType) {
    this.promoCodeType = promoCodeType;
  }

  public boolean isPromoCodeCombine() {
    return isPromoCodeCombine;
  }

  public void setPromoCodeCombine(boolean promoCodeCombine) {
    isPromoCodeCombine = promoCodeCombine;
  }

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<UsageRule> getUsageRules() {
    return usageRules;
  }

  public void setUsageRules(List<UsageRule> usageRules) {
    this.usageRules = usageRules;
  }

  public Set<PromoCodeDistribution> getPromoCodeDistributions() {
    return promoCodeDistributions;
  }

  public void setPromoCodeDistributions(
      Set<PromoCodeDistribution> promoCodeDistributions) {
    this.promoCodeDistributions = promoCodeDistributions;
  }

  public Double getMaxDiscount() {
    return maxDiscount;
  }

  public void setMaxDiscount(Double maxDiscount) {
    this.maxDiscount = maxDiscount;
  }

  public List<PromoCodeGroupRule> getPromoCodeGroupRules() {
    return promoCodeGroupRules;
  }

  public void setPromoCodeGroupRules(
      List<PromoCodeGroupRule> promoCodeGroupRules) {
    this.promoCodeGroupRules = promoCodeGroupRules;
  }

  public PromoCodeAdjustmentStatus getPromoCodeAdjustmentStatus() {
    return promoCodeAdjustmentStatus;
  }

  public void setPromoCodeAdjustmentStatus(
      PromoCodeAdjustmentStatus promoCodeAdjustmentStatus) {
    this.promoCodeAdjustmentStatus = promoCodeAdjustmentStatus;
  }

  public CalculateType getCalculateType() {
    return calculateType;
  }

  public void setCalculateType(CalculateType calculateType) {
    this.calculateType = calculateType;
  }

  public List<PromoCodePriceRange> getPromoCodePriceRanges() {
    return promoCodePriceRanges;
  }

  public void setPromoCodePriceRanges(
      List<PromoCodePriceRange> promoCodePriceRanges) {
    this.promoCodePriceRanges = promoCodePriceRanges;
  }

  public PromoCodeCost getPromoCodeCost() {
    return promoCodeCost;
  }

  public void setPromoCodeCost(PromoCodeCost promoCodeCost) {
    this.promoCodeCost = promoCodeCost;
  }

  public List<PaymentMethod> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(
      List<PaymentMethod> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public Boolean getValidAllOrderDetails() {
    return isValidAllOrderDetails;
  }

  public void setValidAllOrderDetails(Boolean validAllOrderDetails) {
    isValidAllOrderDetails = validAllOrderDetails;
  }

  @Override
  public String toString() {
    return "PromoCodeAdjustment{" +
        "promoCodeType=" + promoCodeType +
        ", isPromoCodeCombine=" + isPromoCodeCombine +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", usageRules=" + usageRules +
        ", promoCodeDistributions=" + promoCodeDistributions +
        ", maxDiscount=" + maxDiscount +
        ", promoCodeGroupRules=" + promoCodeGroupRules +
        ", promoCodeAdjustmentStatus=" + promoCodeAdjustmentStatus +
        ", calculateType=" + calculateType +
        ", promoCodePriceRanges=" + promoCodePriceRanges +
        ", promoCodeCost=" + promoCodeCost +
        ", paymentMethods=" + paymentMethods +
        ", isValidAllOrderDetails=" + isValidAllOrderDetails +
        '}';
  }
}
