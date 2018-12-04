package com.tl.booking.promo.code.entity;

import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeRuleValidation {

  List<String> invalidParams;
  Boolean isValid;

  public List<String> getInvalidParams() {
    return invalidParams;
  }

  public void setInvalidParams(List<String> invalidParams) {
    this.invalidParams = invalidParams;
  }

  public Boolean getValid() {
    return isValid;
  }

  public void setValid(Boolean valid) {
    isValid = valid;
  }

  @Override
  public String toString() {
    return "PromoCodeRuleValidation{" +
        "invalidParams=" + invalidParams +
        ", isValid=" + isValid +
        '}';
  }
}
