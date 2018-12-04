package com.tl.booking.promo.code.entity.outbound;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.SystemParameterFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.SYSTEM_PARAMETER)
public class ExternalSystemParameter extends BaseMongo {

  @Field(value = SystemParameterFields.VARIABLE)
  private String variable;

  @Field(value = SystemParameterFields.VALUE)
  private String value;

  @Field(value = SystemParameterFields.DESCRIPTION)
  private String description;

  public String getVariable() {
    return variable;
  }

  public void setVariable(String variable) {
    this.variable = variable;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "SystemParameter{" +
        "variable='" + variable + '\'' +
        ", value='" + value + '\'' +
        ", description='" + description + '\'' +
        "} " + super.toString();
  }
}
