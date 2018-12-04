package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PaymentResponse extends BaseMongoResponse {

  private String paymentId;
  private String name;
  private Boolean useBinNumber;

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getUseBinNumber() {
    return useBinNumber;
  }

  public void setUseBinNumber(Boolean useBinNumber) {
    this.useBinNumber = useBinNumber;
  }

  @Override
  public String toString() {
    return "Payment{" +
        "paymentId='" + paymentId + '\'' +
        ", name='" + name + '\'' +
        ", useBinNumber=" + useBinNumber +
        '}';
  }
}
