package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ProductTypeResponse extends BaseMongoResponse {

  private String description;
  private String name;
  private String value;
  private String label;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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
    return "ProductTypeResponse{" +
        "description='" + description + '\'' +
        ", name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", label='" + label + '\'' +
        '}';
  }
}
