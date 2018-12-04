package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class AdditionalDiscountRequest extends CommonModel {

  private Integer hotelId;
  private Double minDiscount;
  private Double valueDiscount;
  private String type;
  private Integer active;

  public Integer getHotelId() {
    return hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }

  public Double getMinDiscount() {
    return minDiscount;
  }

  public void setMinDiscount(Double minDiscount) {
    this.minDiscount = minDiscount;
  }

  public Double getValueDiscount() {
    return valueDiscount;
  }

  public void setValueDiscount(Double valueDiscount) {
    this.valueDiscount = valueDiscount;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "AdditionalDiscountRequest{" +
        "hotelId=" + hotelId +
        ", minDiscount=" + minDiscount +
        ", valueDiscount=" + valueDiscount +
        ", type=" + type +
        ", active=" + active +
        '}';
  }
}
