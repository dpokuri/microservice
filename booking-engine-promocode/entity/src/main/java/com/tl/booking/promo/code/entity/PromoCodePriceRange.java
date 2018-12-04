package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Pattern;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class PromoCodePriceRange extends CommonModel implements Serializable {

  @NotBlank
  @NotEmpty
  @DecimalMax("1000000000.00")
  @Pattern(regexp = "\\d+(\\.\\d{1,2})?") // pattern for decimal format
  private Double minPrice;

  @NotBlank
  @NotEmpty
  @DecimalMax("1000000000.00")
  @Pattern(regexp = "\\d+(\\.\\d{1,2})?") // pattern for decimal format
  private Double maxPrice;

  @NotBlank
  @NotEmpty
  private PromoCodeDiscount promoCodeDiscount;

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

  public PromoCodeDiscount getPromoCodeDiscount() {
    return promoCodeDiscount;
  }

  public void setPromoCodeDiscount(PromoCodeDiscount promoCodeDiscount) {
    this.promoCodeDiscount = promoCodeDiscount;
  }

  @Override
  public String toString() {
    return "PromoCodePriceRange{" +
        "minPrice=" + minPrice +
        ", maxPrice=" + maxPrice +
        ", discount=" + promoCodeDiscount +
        '}';
  }
}
