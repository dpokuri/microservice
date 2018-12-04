package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.StoreIdFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.STORE_ID)
public class StoreId extends BaseMongo {

  @Field(value = StoreIdFields.VALUE)
  private String value;

  @Field(value = StoreIdFields.LABEL)
  private String label;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "StoreId{" +
        "value='" + value + '\'' +
        ", label='" + label + '\'' +
        '}';
  }
}
