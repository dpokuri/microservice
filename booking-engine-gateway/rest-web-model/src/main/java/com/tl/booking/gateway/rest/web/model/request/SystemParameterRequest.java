package com.tl.booking.gateway.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public class SystemParameterRequest extends CommonModel {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @NotNull
  private String variable;

  @NotBlank
  @NotNull
  private String value;

  @NotBlank
  @NotNull
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
    return "CreateSystemParameterRequest{" +
        "variable='" + variable + '\'' +
        ", value='" + value + '\'' +
        ", description='" + description + '\'' +
        "} " + super.toString();
  }
}
