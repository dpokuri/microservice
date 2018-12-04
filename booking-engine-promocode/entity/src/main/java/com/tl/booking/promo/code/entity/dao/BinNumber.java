package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.BinNumberFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.BIN_NUMBER)
public class BinNumber extends BaseMongo {

  @Field(value = BinNumberFields.BIN_NUMBER)
  private String binNumber;
  @Field(value = BinNumberFields.DESCRIPTION)
  private String description;
  @Field(value = BinNumberFields.BANK_ID)
  private String bankId;
  @Field(value = BinNumberFields.CARD_TYPE_ID)
  private String cardTypeId;

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

  @Override
  public String toString() {
    return "BinNumber{" +
        "binNumber='" + binNumber + '\'' +
        ", description='" + description + '\'' +
        ", bankId='" + bankId + '\'' +
        ", cardTypeId='" + cardTypeId + '\'' +
        '}';
  }
}
