package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class PromoCodeRuleValueRequest extends CommonModel implements Serializable {

  @NotBlank
  @NotEmpty
  @NotNull
  private String operator;

  @NotBlank
  @NotEmpty
  @NotNull
  private String value;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PromoCodeRuleValueRequest{" +
        "operator='" + operator + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
