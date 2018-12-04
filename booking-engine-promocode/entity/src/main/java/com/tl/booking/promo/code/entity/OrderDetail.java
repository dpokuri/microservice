package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class OrderDetail extends CommonModel implements Serializable {

  private String referenceId;
  private Map<String, String> orderAttribute;
  private Double amount;
  private List<CostAmount> partnerCosts = new ArrayList<>();
  private List<CostAmount> companyCosts = new ArrayList<>();
  private Double discount;
  private PromoCodeGroupRule promoCodeGroupRule;
  private Boolean isPromoCodeRuleValid;

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public Map<String, String> getOrderAttribute() {
    return orderAttribute;
  }

  public void setOrderAttribute(Map<String, String> orderAttribute) {
    this.orderAttribute = orderAttribute;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public List<CostAmount> getPartnerCosts() {
    return partnerCosts;
  }

  public void setPartnerCosts(List<CostAmount> partnerCosts) {
    this.partnerCosts = partnerCosts;
  }

  public List<CostAmount> getCompanyCosts() {
    return companyCosts;
  }

  public void setCompanyCosts(List<CostAmount> companyCosts) {
    this.companyCosts = companyCosts;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public PromoCodeGroupRule getPromoCodeGroupRule() {
    return promoCodeGroupRule;
  }

  public void setPromoCodeGroupRule(PromoCodeGroupRule promoCodeGroupRule) {
    this.promoCodeGroupRule = promoCodeGroupRule;
  }

  public Boolean getPromoCodeRuleValid() {
    return isPromoCodeRuleValid;
  }

  public void setPromoCodeRuleValid(Boolean promoCodeRuleValid) {
    isPromoCodeRuleValid = promoCodeRuleValid;
  }

  @Override
  public String toString() {
    return "OrderDetail{" +
        "referenceId='" + referenceId + '\'' +
        ", orderAttribute=" + orderAttribute +
        ", amount=" + amount +
        ", partnerCosts=" + partnerCosts +
        ", companyCosts=" + companyCosts +
        ", discount=" + discount +
        ", promoCodeGroupRule=" + promoCodeGroupRule +
        ", isPromoCodeRuleValid=" + isPromoCodeRuleValid +
        '}';
  }
}
