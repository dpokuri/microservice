package com.tl.booking.gateway.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ExternalSystemParameterResponse extends CommonModel {

  private static final long serialVersionUID = 1L;
  private String variable;
  private String value;
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
    return "SystemParameterResponse{" +
        "variable='" + variable + '\'' +
        ", value='" + value + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
