package com.tl.booking.gateway.entity.outbound.PromoCode;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CostRequest extends CommonModel implements Serializable {

  @NotNull
  private Double value;

  @NotNull
  private Double percent;

  @NotNull
  private Double maxValue;

  @NotNull
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
    return "CostRequest{" +
        "value=" + value +
        ", percent=" + percent +
        ", maxValue=" + maxValue +
        ", name='" + name + '\'' +
        '}' + super.toString();
  }
}
