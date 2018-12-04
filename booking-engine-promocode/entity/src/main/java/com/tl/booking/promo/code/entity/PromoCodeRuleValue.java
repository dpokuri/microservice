package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeRuleValue extends CommonModel {

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
    return "PromoCodeRuleValue{" +
        "operator='" + operator + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
