package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class PromoCodeGroupRuleRequest extends CommonModel implements Serializable {

  @NotNull
  @NotEmpty
  @NotBlank
  private String name;

  @Valid
  private PromoCodeCostRequest promoCodeCost;

  @Valid
  private List<PromoCodePriceRangeRequest> promoCodePriceRanges;

  private List<PromoCodeRuleRequest> promoCodeRules;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PromoCodeCostRequest getPromoCodeCost() {
    return promoCodeCost;
  }

  public void setPromoCodeCost(
      PromoCodeCostRequest promoCodeCost) {
    this.promoCodeCost = promoCodeCost;
  }

  public List<PromoCodePriceRangeRequest> getPromoCodePriceRanges() {
    return promoCodePriceRanges;
  }

  public void setPromoCodePriceRanges(
      List<PromoCodePriceRangeRequest> promoCodePriceRanges) {
    this.promoCodePriceRanges = promoCodePriceRanges;
  }

  public List<PromoCodeRuleRequest> getPromoCodeRules() {
    return promoCodeRules;
  }

  public void setPromoCodeRules(
      List<PromoCodeRuleRequest> promoCodeRules) {
    this.promoCodeRules = promoCodeRules;
  }

  @Override
  public String toString() {
    return "PromoCodeGroupRuleRequest{" +
        "name='" + name + '\'' +
        ", promoCodeCost=" + promoCodeCost +
        ", promoCodePriceRanges=" + promoCodePriceRanges +
        ", promoCodeRules=" + promoCodeRules +
        '}' + super.toString();
  }
}
