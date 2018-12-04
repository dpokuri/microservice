package com.tl.booking.gateway.entity.outbound.PromoCode;

import java.util.Set;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PaymentMethodResponse {

  private Integer paymentId;

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
    return "PaymentMethodResponse{" +
        "paymentId=" + paymentId +
        ", binNumbers=" + binNumbers +
        '}' + super.toString();
  }
}
