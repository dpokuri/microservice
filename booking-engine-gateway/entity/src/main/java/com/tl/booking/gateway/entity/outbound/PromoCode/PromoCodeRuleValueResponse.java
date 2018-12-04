package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeRuleValueResponse extends CommonModel implements Serializable {

  private String operator;
  private Object value;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PromoCodeRuleValueResponse{" +
        "operator='" + operator + '\'' +
        ", value=" + value +
        '}' + super.toString();
  }
}
