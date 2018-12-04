package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.CardTypeFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.CARD_TYPE)
public class CardType extends BaseMongo {

  @Field(value = CardTypeFields.NAME)
  private String name;

  @Field(value = CardTypeFields.BANK_ID)
  private String bankId;

  @Override
  public String toString() {
    return "CardType{" +
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
