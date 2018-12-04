package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class PaymentRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  private String paymentId;

  @NotNull
  @NotBlank
  @NotEmpty
  private String name;

  @NotNull
  private Boolean useBinNumber;

  @Override
  public String toString() {
    return "PaymentRequest{" +
        "paymentId='" + paymentId + '\'' +
        ", name='" + name + '\'' +
        ", useBinNumber=" + useBinNumber +
        '}';
  }

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
}
