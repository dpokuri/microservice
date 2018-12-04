package com.tl.booking.gateway.entity.outbound.PromoCode;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeDiscountResponse extends CommonModel implements Serializable {

  private Double value;
  private Double percent;
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
    return "PromoCodeDiscountResponse{" +
        "value=" + value +
        ", percent=" + percent +
        ", maxValue=" + maxValue +
        '}' + super.toString();
  }
}
