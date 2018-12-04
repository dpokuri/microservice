package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;


public class PromoCodePriceRangeResponse extends CommonModel implements Serializable {

  private Double minPrice;
  private Double maxPrice;
  private PromoCodeDiscountResponse promoCodeDiscount;

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

  public PromoCodeDiscountResponse getPromoCodeDiscount() {
    return promoCodeDiscount;
  }

  public void setPromoCodeDiscount(
      PromoCodeDiscountResponse promoCodeDiscount) {
    this.promoCodeDiscount = promoCodeDiscount;
  }

  @Override
  public String toString() {
    return "PromoCodePriceRangeResponse{" +
        "minPrice=" + minPrice +
        ", maxPrice=" + maxPrice +
        ", promoCodeDiscount=" + promoCodeDiscount +
        '}' + super.toString();
  }
}
