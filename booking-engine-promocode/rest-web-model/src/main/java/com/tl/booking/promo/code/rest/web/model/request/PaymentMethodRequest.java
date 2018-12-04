package com.tl.booking.promo.code.rest.web.model.request;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PaymentMethodRequest {

  @NotNull
  private Integer paymentId;

  @Valid
  private Set<String> binNumbers;

  public Integer getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(Integer paymentId) {
    this.paymentId = paymentId;
  }

  public Set<String> getBinNumbers() {
    return binNumbers;
  }

  public void setBinNumbers(Set<String> binNumbers) {
    this.binNumbers = binNumbers;
  }

  @Override
  public String toString() {
    return "PaymentMethodRequest{" +
        "paymentId=" + paymentId +
        ", binNumbers=" + binNumbers +
        '}' + super.toString();
  }
}
