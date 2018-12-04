package com.tl.booking.gateway.entity.outbound.PromoCode;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodePriceRangeRequest extends CommonModel implements Serializable {

  @NotNull
  private Double minPrice;

  @NotNull
  private Double maxPrice;

  @Valid
  @NotNull
  private PromoCodeDiscountRequest promoCodeDiscount;

  public Double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(Double minPrice) {
    this.minPrice = minPrice;
  }

  public Double getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(Double maxPrice) {
    this.maxPrice = maxPrice;
  }

  public PromoCodeDiscountRequest getPromoCodeDiscount() {
    return promoCodeDiscount;
  }

  public void setPromoCodeDiscount(
      PromoCodeDiscountRequest promoCodeDiscount) {
    this.promoCodeDiscount = promoCodeDiscount;
  }

  @Override
  public String toString() {
    return "PromoCodePriceRangeRequest{" +
        "minPrice=" + minPrice +
        ", maxPrice=" + maxPrice +
        ", promoCodeDiscount=" + promoCodeDiscount +
        '}' + super.toString();
  }
}
