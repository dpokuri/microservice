package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class CardTypeRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  private String name;

  @NotNull
  @NotBlank
  @NotEmpty
  private String bankId;

  @Override
  public String toString() {
    return "CardTypeRequest{" +
        "name='" + name + '\'' +
        ", bankId='" + bankId + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }
}
