package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.promo.code.entity.constant.enums.CalculateType;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeType;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class PromoCodeAdjustmentUpdateRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  @NotNull
  private String id;

  @Valid
  @NotNull
  private PromoCodeType promoCodeType;

  @NotNull
  private boolean isPromoCodeCombine;

  @NotNull
  @NotBlank
  @NotEmpty
  private String code;

  @NotNull
  @NotBlank
  @NotEmpty
  private String name;

  @NotNull
  @NotBlank
  @NotEmpty
  private String description;


  @Valid
  @NotNull
  private List<UsageRuleRequest> usageRules;

  @Valid
  @NotNull
  private Set<PromoCodeDistributionRequest> promoCodeDistributions;

  @NotNull
  private Double maxDiscount;

  @Valid
  @NotNull
  private List<PromoCodeGroupRuleRequest> promoCodeGroupRules;

  @Valid
  private List<PaymentMethodRequest> paymentMethods;

  @Valid
  @NotNull
  private CalculateType calculateType;

  private List<PromoCodePriceRangeRequest> promoCodePriceRanges;

  private PromoCodeCostRequest promoCodeCost;

  @Valid
  @NotNull
  private Boolean isValidAllOrderDetails;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public List<UsageRuleRequest> getUsageRules() {
    return usageRules;
  }

  public void setUsageRules(
      List<UsageRuleRequest> usageRules) {
    this.usageRules = usageRules;
  }

  public Set<PromoCodeDistributionRequest> getPromoCodeDistributions() {
    return promoCodeDistributions;
  }

  public void setPromoCodeDistributions(
      Set<PromoCodeDistributionRequest> promoCodeDistributions) {
    this.promoCodeDistributions = promoCodeDistributions;
  }

  public Double getMaxDiscount() {
    return maxDiscount;
  }

  public void setMaxDiscount(Double maxDiscount) {
    this.maxDiscount = maxDiscount;
  }

  public List<PromoCodeGroupRuleRequest> getPromoCodeGroupRules() {
    return promoCodeGroupRules;
  }

  public void setPromoCodeGroupRules(
      List<PromoCodeGroupRuleRequest> promoCodeGroupRules) {
    this.promoCodeGroupRules = promoCodeGroupRules;
  }

  public List<PaymentMethodRequest> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(
      List<PaymentMethodRequest> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public CalculateType getCalculateType() {
    return calculateType;
  }

  public void setCalculateType(CalculateType calculateType) {
    this.calculateType = calculateType;
  }

  public List<PromoCodePriceRangeRequest> getPromoCodePriceRanges() {
    return promoCodePriceRanges;
  }

  public void setPromoCodePriceRanges(
      List<PromoCodePriceRangeRequest> promoCodePriceRanges) {
    this.promoCodePriceRanges = promoCodePriceRanges;
  }

  public PromoCodeCostRequest getPromoCodeCost() {
    return promoCodeCost;
  }

  public void setPromoCodeCost(
      PromoCodeCostRequest promoCodeCost) {
    this.promoCodeCost = promoCodeCost;
  }

  public Boolean getValidAllOrderDetails() {
    return isValidAllOrderDetails;
  }

  public void setValidAllOrderDetails(Boolean validAllOrderDetails) {
    isValidAllOrderDetails = validAllOrderDetails;
  }

  @Override
  public String toString() {
    return "PromoCodeAdjustmentUpdateRequest{" +
        "id='" + id + '\'' +
        ", promoCodeType=" + promoCodeType +
        ", isPromoCodeCombine=" + isPromoCodeCombine +
        ", code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", usageRules=" + usageRules +
        ", promoCodeDistributions=" + promoCodeDistributions +
        ", maxDiscount=" + maxDiscount +
        ", promoCodeGroupRules=" + promoCodeGroupRules +
        ", paymentMethods=" + paymentMethods +
        ", calculateType=" + calculateType +
        ", promoCodePriceRanges=" + promoCodePriceRanges +
        ", promoCodeCost=" + promoCodeCost +
        ", isValidAllOrderDetails=" + isValidAllOrderDetails +
        '}';
  }
}
