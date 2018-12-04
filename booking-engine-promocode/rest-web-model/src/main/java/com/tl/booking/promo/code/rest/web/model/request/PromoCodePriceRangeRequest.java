package com.tl.booking.promo.code.rest.web.model.request;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class PromoCodePriceRangeRequest extends CommonModel implements Serializable {

  @NotNull
  @DecimalMax("10000000000.0") @DecimalMin("0.0")
  private Double minPrice;

  @NotNull
  @DecimalMax("10000000000.0") @DecimalMin("0.0")
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
