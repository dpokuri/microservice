package com.tl.booking.gateway.entity.outbound.flightrme;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PriceRangeResponse extends BaseMongoResponse {

  private Double startPrice;

  private Double endPrice;

  private Double maxValue;

  private Double percent;

  private Double value;

  public Double getStartPrice() {
    return startPrice;
  }

  public void setStartPrice(Double startPrice) {
    this.startPrice = startPrice;
  }

  public Double getEndPrice() {
    return endPrice;
  }

  public void setEndPrice(Double endPrice) {
    this.endPrice = endPrice;
  }

  public Double getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Double maxValue) {
    this.maxValue = maxValue;
  }

  public Double getPercent() {
    return percent;
  }

  public void setPercent(Double percent) {
    this.percent = percent;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PriceRange{" +
        "startPrice=" + startPrice +
        ", endPrice=" + endPrice +
        ", maxValue=" + maxValue +
        ", percent=" + percent +
        ", value=" + value +
        '}' + super.toString();
  }

}
