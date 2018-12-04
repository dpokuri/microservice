package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.configuration.NotEmptySetFields;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PaymentMethodRequest extends CommonModel implements Serializable {

  @NotNull
  private Integer paymentId;

  @NotNull
  @NotEmptySetFields
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
