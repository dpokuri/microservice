package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeGroupRuleObject extends CommonModel implements Serializable {

  private String name;
  private PromoCodeCost promoCodeCost;
  private List<PromoCodePriceRange> promoCodePriceRanges;
  private List<Map<String, Object>> promoCodeRules;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PromoCodeCost getPromoCodeCost() {
    return promoCodeCost;
  }

  public void setPromoCodeCost(PromoCodeCost promoCodeCost) {
    this.promoCodeCost = promoCodeCost;
  }

  public List<PromoCodePriceRange> getPromoCodePriceRanges() {
    return promoCodePriceRanges;
  }

  public void setPromoCodePriceRanges(
      List<PromoCodePriceRange> promoCodePriceRanges) {
    this.promoCodePriceRanges = promoCodePriceRanges;
  }

  public List<Map<String, Object>> getPromoCodeRules() {
    return promoCodeRules;
  }

  public void setPromoCodeRules(
      List<Map<String, Object>> promoCodeRules) {
    this.promoCodeRules = promoCodeRules;
  }

  @Override
  public String toString() {
    return "PromoCodeGroupRuleObject{" +
        "name='" + name + '\'' +
        ", promoCodeCost=" + promoCodeCost +
        ", promoCodePriceRanges=" + promoCodePriceRanges +
        ", promoCodeRules=" + promoCodeRules +
        '}';
  }
}
