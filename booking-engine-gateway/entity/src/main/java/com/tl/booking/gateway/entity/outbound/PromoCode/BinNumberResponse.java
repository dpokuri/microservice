package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class BinNumberResponse extends BaseMongoResponse {
  private String binNumber;
  private String description;
  private String bankId;
  private String cardTypeId;

  @Override
  public String toString() {
    return "BinNumberResponse{" +
        "binNumber='" + binNumber + '\'' +
        ", description='" + description + '\'' +
        ", bankId='" + bankId + '\'' +
        ", cardTypeId='" + cardTypeId + '\'' +
        '}';
  }

  public String getBinNumber() {
    return binNumber;
  }

  public void setBinNumber(String binNumber) {
    this.binNumber = binNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  public String getCardTypeId() {
    return cardTypeId;
  }

  public void setCardTypeId(String cardTypeId) {
    this.cardTypeId = cardTypeId;
  }
}
