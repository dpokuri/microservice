package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeGroupRuleResponse extends CommonModel implements Serializable {

  private String name;
  private PromoCodeCostResponse promoCodeCost;
  private List<PromoCodePriceRangeResponse> promoCodePriceRanges;
  private List<PromoCodeRuleResponse> promoCodeRules;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PromoCodeCostResponse getPromoCodeCost() {
    return promoCodeCost;
  }

  public void setPromoCodeCost(
      PromoCodeCostResponse promoCodeCost) {
    this.promoCodeCost = promoCodeCost;
  }

  public List<PromoCodePriceRangeResponse> getPromoCodePriceRanges() {
    return promoCodePriceRanges;
  }

  public void setPromoCodePriceRanges(
      List<PromoCodePriceRangeResponse> promoCodePriceRanges) {
    this.promoCodePriceRanges = promoCodePriceRanges;
  }

  public List<PromoCodeRuleResponse> getPromoCodeRules() {
    return promoCodeRules;
  }

  public void setPromoCodeRules(
      List<PromoCodeRuleResponse> promoCodeRules) {
    this.promoCodeRules = promoCodeRules;
  }

  @Override
  public String toString() {
    return "PromoCodeGroupRuleResponse{" +
        "name='" + name + '\'' +
        ", promoCodeCost=" + promoCodeCost +
        ", promoCodePriceRanges=" + promoCodePriceRanges +
        ", promoCodeRules=" + promoCodeRules +
        '}' + super.toString();
  }

}
