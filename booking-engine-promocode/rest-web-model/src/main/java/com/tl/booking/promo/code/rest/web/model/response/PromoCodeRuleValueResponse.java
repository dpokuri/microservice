package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;

public class PromoCodeRuleValueResponse extends CommonModel implements Serializable {

  private String operator;
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
    return "PromoCodeRuleValueResponse{" +
        "operator='" + operator + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
