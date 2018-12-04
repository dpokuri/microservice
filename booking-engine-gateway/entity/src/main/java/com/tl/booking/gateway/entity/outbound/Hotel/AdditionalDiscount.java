package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class AdditionalDiscount extends CommonModel {

  private Integer hotelId;
  private Double valueDiscount;
  private Double minDiscount;
  private Integer active;
  private String type;

  public Integer getHotelId() {
    return hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }

  public Double getValueDiscount() {
    return valueDiscount;
  }

  public void setValueDiscount(Double valueDiscount) {
    this.valueDiscount = valueDiscount;
  }

  public Double getMinDiscount() {
    return minDiscount;
  }

  public void setMinDiscount(Double minDiscount) {
    this.minDiscount = minDiscount;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "AdditionalDiscount{" +
        "hotelId=" + hotelId +
        ", valueDiscount=" + valueDiscount +
        ", minDiscount=" + minDiscount +
        ", active=" + active +
        ", type='" + type + '\'' +
        '}';
  }
}
