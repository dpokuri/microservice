package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class PromoCodeGroupRuleRequest extends CommonModel implements Serializable {

  @NotNull
  @NotEmpty
  @NotBlank
  private String name;

  @Valid
  @NotNull
  private PromoCodeCostRequest promoCodeCost;

  @Valid
  @NotNull
  private List<PromoCodePriceRangeRequest> promoCodePriceRanges;

  @Valid
  @NotNull
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
