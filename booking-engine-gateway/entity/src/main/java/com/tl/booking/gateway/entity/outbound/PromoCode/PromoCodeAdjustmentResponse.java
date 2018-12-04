package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.gateway.entity.constant.enums.CalculateType;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeType;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.util.List;
import java.util.Set;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeAdjustmentResponse extends BaseMongoResponse {
  private PromoCodeType promoCodeType;
  private boolean isPromoCodeCombine;
  private String code;
  private String name;
  private String description;
  private List<UsageRuleResponse> usageRules;
  private Set<PromoCodeDistributionResponse> promoCodeDistributions;
  private Double maxDiscount;
  private List<PromoCodeGroupRuleResponse> promoCodeGroupRules;
  private PromoCodeAdjustmentStatus promoCodeAdjustmentStatus;
  private CalculateType calculateType;
  private List<PromoCodePriceRangeResponse> promoCodePriceRanges;
  private PromoCodeCostResponse promoCodeCost;
  private Boolean isValidAllOrderDetails;
  private List<PaymentMethodResponse> paymentMethods;
  private List<String> allowedActions;

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

  public List<UsageRuleResponse> getUsageRules() {
    return usageRules;
  }

  public void setUsageRules(
      List<UsageRuleResponse> usageRules) {
    this.usageRules = usageRules;
  }

  public Set<PromoCodeDistributionResponse> getPromoCodeDistributions() {
    return promoCodeDistributions;
  }

  public void setPromoCodeDistributions(
      Set<PromoCodeDistributionResponse> promoCodeDistributions) {
    this.promoCodeDistributions = promoCodeDistributions;
  }

  public Double getMaxDiscount() {
    return maxDiscount;
  }

  public void setMaxDiscount(Double maxDiscount) {
    this.maxDiscount = maxDiscount;
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


  public Boolean getValidAllOrderDetails() {
    return isValidAllOrderDetails;
  }

  public void setValidAllOrderDetails(Boolean validAllOrderDetails) {
    isValidAllOrderDetails = validAllOrderDetails;
  }

  public List<PaymentMethodResponse> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(
      List<PaymentMethodResponse> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public List<PromoCodeGroupRuleResponse> getPromoCodeGroupRules() {
    return promoCodeGroupRules;
  }

  public void setPromoCodeGroupRules(
      List<PromoCodeGroupRuleResponse> promoCodeGroupRules) {
    this.promoCodeGroupRules = promoCodeGroupRules;
  }

  public List<PromoCodePriceRangeResponse> getPromoCodePriceRanges() {
    return promoCodePriceRanges;
  }

  public void setPromoCodePriceRanges(
      List<PromoCodePriceRangeResponse> promoCodePriceRanges) {
    this.promoCodePriceRanges = promoCodePriceRanges;
  }

  public PromoCodeCostResponse getPromoCodeCost() {
    return promoCodeCost;
  }

  public void setPromoCodeCost(
      PromoCodeCostResponse promoCodeCost) {
    this.promoCodeCost = promoCodeCost;
  }

  public List<String> getAllowedActions() {
    return allowedActions;
  }

  public void setAllowedActions(List<String> allowedActions) {
    this.allowedActions = allowedActions;
  }

  @Override
  public String toString() {
    return "PromoCodeAdjustmentResponse{" +
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
        ", isValidAllOrderDetails=" + isValidAllOrderDetails +
        ", paymentMethods=" + paymentMethods +
        ", allowedActions=" + allowedActions +
        '}' + super.toString();
  }
}
