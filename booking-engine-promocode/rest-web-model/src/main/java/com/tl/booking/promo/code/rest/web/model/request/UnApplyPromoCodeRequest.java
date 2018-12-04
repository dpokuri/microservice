package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class UnApplyPromoCodeRequest extends CommonModel implements Serializable {
  private String cardNumber;

  @NotNull
  @NotEmpty
  @NotBlank
  private String code;

  private String referenceId;

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  @Override
  public String toString() {
    return "UnApplyPromoCodeRequest{" +
        "cardNumber='" + cardNumber + '\'' +
        ", code='" + code + '\'' +
        ", referenceId='" + referenceId + '\'' +
        '}';
  }
}
