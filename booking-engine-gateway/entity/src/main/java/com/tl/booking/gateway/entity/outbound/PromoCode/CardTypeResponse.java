package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CardTypeResponse extends BaseMongoResponse {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "CardType{" +
        "name='" + name + '\'' +
        '}';
  }
}
