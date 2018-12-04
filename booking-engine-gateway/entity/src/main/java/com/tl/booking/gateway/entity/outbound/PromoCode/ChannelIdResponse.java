package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ChannelIdResponse extends BaseMongoResponse {

  private String label;
  private String value;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "StoreIdResponse{" +
        "label='" + label + '\'' +
        ", value='" + value + '\'' +
        '}' + super.toString();
  }
}
