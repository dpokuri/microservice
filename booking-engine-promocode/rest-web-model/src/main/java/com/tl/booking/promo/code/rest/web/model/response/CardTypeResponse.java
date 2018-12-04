package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CardTypeResponse extends BaseMongoResponse {

  private String name;

  private String bankId;

  @Override
  public String toString() {
    return "CardTypeResponse{" +
        "name='" + name + '\'' +
        ", bankId='" + bankId + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }
}
