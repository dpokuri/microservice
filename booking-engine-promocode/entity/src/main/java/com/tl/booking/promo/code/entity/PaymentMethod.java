package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Set;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PaymentMethod extends CommonModel implements Serializable {

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
    return "PaymentMethod{" +
        "paymentId=" + paymentId +
        ", binNumbers=" + binNumbers +
        '}';
  }
}
