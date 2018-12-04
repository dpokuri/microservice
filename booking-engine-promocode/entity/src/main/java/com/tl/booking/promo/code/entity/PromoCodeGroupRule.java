package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class PromoCodeGroupRule extends CommonModel implements Serializable {

  @NotNull
  @NotEmpty
  @NotBlank
  private String name;

  private PromoCodeCost promoCodeCost;

  @NotNull
  @NotEmpty
  @NotBlank
  private List<PromoCodePriceRange> promoCodePriceRanges;

  @NotNull
  @NotEmpty
  @NotBlank
  private List<PromoCodeRule> promoCodeRules;

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

  public List<PromoCodeRule> getPromoCodeRules() {
    return promoCodeRules;
  }

  public void setPromoCodeRules(
      List<PromoCodeRule> promoCodeRules) {
    this.promoCodeRules = promoCodeRules;
  }

  @Override
  public String toString() {
    return "PromoCodeGroupRule{" +
        "name='" + name + '\'' +
        ", promoCodeCost=" + promoCodeCost +
        ", promoCodePriceRanges=" + promoCodePriceRanges +
        ", promoCodeRules=" + promoCodeRules +
        '}';
  }
}
