package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PaymentDTO extends CommonModel implements Serializable {

  private Integer paymentId;
  private String cardNumber;
  private String binNumber;

  public Integer getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(Integer paymentId) {
    this.paymentId = paymentId;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getBinNumber() {
    return binNumber;
  }

  public void setBinNumber(String binNumber) {
    this.binNumber = binNumber;
  }

  @Override
  public String toString() {
    return "PaymentDTO{" +
        "paymentId=" + paymentId +
        ", cardNumber='" + cardNumber + '\'' +
        ", binNumber='" + binNumber + '\'' +
        '}';
  }
}
