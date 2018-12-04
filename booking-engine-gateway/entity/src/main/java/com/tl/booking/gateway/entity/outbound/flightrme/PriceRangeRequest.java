package com.tl.booking.gateway.entity.outbound.flightrme;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PriceRangeRequest extends CommonModel implements Serializable {

  @NotNull
  private Double startPrice;

  @NotNull
  private Double endPrice;

  @NotNull
  private Double maxValue;

  @NotNull
  private Double percent;

  @NotNull
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
