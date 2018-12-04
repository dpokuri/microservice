package com.tl.booking.promo.code.rest.web.model.response;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ProductTypeFindAllResponse {

  private String value;
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
    return "ProductTypeFindAllResponse{" +
        "value='" + value + '\'' +
        ", label='" + label + '\'' +
        '}';
  }
}
