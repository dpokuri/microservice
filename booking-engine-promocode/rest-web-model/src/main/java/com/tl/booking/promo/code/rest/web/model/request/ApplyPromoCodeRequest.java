package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.promo.code.entity.PaymentDTO;
import com.tl.booking.promo.code.rest.web.model.OrderDetailDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class ApplyPromoCodeRequest extends CommonModel implements Serializable {

  @NotBlank
  @NotNull
  @NotEmpty
  private String code;

  @NotNull
  @Valid
  private List<OrderDetailDTO> orderDetails;

  @Valid
  @NotNull
  private PaymentDTO payment;

  @NotNull
  private Double totalPrice;

  @NotNull
  private Set<String> usedPromoCodes;

  @Valid
  @NotNull
  private String referenceId;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<OrderDetailDTO> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(
      List<OrderDetailDTO> orderDetails) {
    this.orderDetails = orderDetails;
  }

  public PaymentDTO getPayment() {
    return payment;
  }

  public void setPayment(PaymentDTO payment) {
    this.payment = payment;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Set<String> getUsedPromoCodes() {
    return usedPromoCodes;
  }

  public void setUsedPromoCodes(Set<String> usedPromoCodes) {
    this.usedPromoCodes = usedPromoCodes;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  @Override
  public String toString() {
    return "ApplyPromoCodeRequest{" +
        "code='" + code + '\'' +
        ", orderDetails=" + orderDetails +
        ", payment=" + payment +
        ", totalPrice=" + totalPrice +
        ", usedPromoCodes=" + usedPromoCodes +
        ", referenceId='" + referenceId + '\'' +
        '}';
  }
}
