package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class Payment extends CommonModel implements Serializable {

  private Integer paymentId;
  private List<String> paymentName;

  public Integer getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(Integer paymentId) {
    this.paymentId = paymentId;
  }

  public List<String> getPaymentName() {
    return paymentName;
  }

  public void setPaymentName(List<String> paymentName) {
    this.paymentName = paymentName;
  }

  @Override
  public String toString() {
    return "Payment{" +
        "paymentId=" + paymentId +
        ", paymentName=" + paymentName +
        '}';
  }
}
