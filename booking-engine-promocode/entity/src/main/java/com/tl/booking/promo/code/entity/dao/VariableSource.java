package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.VariableSourceFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.VARIABLE_SOURCE)
public class VariableSource extends BaseMongo {

  @Field(value = VariableSourceFields.SOURCE_TYPE)
  private String sourceType;

  @Field(value = VariableSourceFields.VALUE_ID)
  private String valueId;

  @Field(value = VariableSourceFields.VALUE_NAME)
  private String valueName;

  @Field(value = VariableSourceFields.VALUE_SEARCH)
  private String valueSearch;

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  public String getValueId() {
    return valueId;
  }

  public void setValueId(String valueId) {
    this.valueId = valueId;
  }

  public String getValueName() {
    return valueName;
  }

  public void setValueName(String valueName) {
    this.valueName = valueName;
  }

  public String getValueSearch() {
    return valueSearch;
  }

  public void setValueSearch(String valueSearch) {
    this.valueSearch = valueSearch;
  }

  @Override
  public String toString() {
    return "VariableSource{" +
        "sourceType='" + sourceType + '\'' +
        ", valueId='" + valueId + '\'' +
        ", valueName='" + valueName + '\'' +
        ", valueSearch='" + valueSearch + '\'' +
        '}';
  }
}
