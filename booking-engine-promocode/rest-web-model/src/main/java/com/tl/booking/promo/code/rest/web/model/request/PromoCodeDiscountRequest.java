package com.tl.booking.promo.code.rest.web.model.request;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class PromoCodeDiscountRequest extends CommonModel implements Serializable {

  @NotNull
  @DecimalMax("10000000000.0") @DecimalMin("0.0")
  private Double value;

  @NotNull
  @DecimalMax("100.0") @DecimalMin("0.0")
  private Double percent;

  @NotNull
  @DecimalMax("10000000000.0") @DecimalMin("0.0")
  private Double maxValue;

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Double getPercent() {
    return percent;
  }

  public void setPercent(Double percent) {
    this.percent = percent;
  }

  public Double getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Double maxValue) {
    this.maxValue = maxValue;
  }

  @Override
  public String toString() {
    return "PromoCodeDiscountRequest{" +
        "value=" + value +
        ", percent=" + percent +
        ", maxValue=" + maxValue +
        '}' + super.toString();
  }
}
