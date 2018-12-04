package com.tl.booking.promo.code.rest.web.model.response;


import java.io.Serializable;

public class CostResponse implements Serializable {

  private Double value;
  private Double percent;
  private Double maxValue;
  private String name;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "CostResponse{" +
        "value=" + value +
        ", percent=" + percent +
        ", maxValue=" + maxValue +
        ", name='" + name + '\'' +
        '}' + super.toString();
  }
}
