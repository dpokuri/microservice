package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PriceTier extends CommonModel {

  private Double min;
  private Double max;
  private Double amount;
  private Integer Percentage;
  private Double maxValue;

  public Double getMin() {
    return min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  public Double getMax() {
    return max;
  }

  public void setMax(Double max) {
    this.max = max;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Integer getPercentage() {
    return Percentage;
  }

  public void setPercentage(Integer percentage) {
    Percentage = percentage;
  }

  public Double getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Double maxValue) {
    this.maxValue = maxValue;
  }

  @Override
  public String toString() {
    return "PriceTier{" +
        "min=" + min +
        ", max=" + max +
        ", amount=" + amount +
        ", Percentage=" + Percentage +
        ", maxValue=" + maxValue +
        '}';
  }
}
