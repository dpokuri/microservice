package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class BinNumberRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  private String binNumber;

  @NotNull
  @NotBlank
  @NotEmpty
  private String description;

  @NotNull
  @NotBlank
  @NotEmpty
  private String bankId;

  @NotNull
  @NotBlank
  @NotEmpty
  private String cardTypeId;

  @Override
  public String toString() {
    return "BinNumberRequest{" +
        "binNumber='" + binNumber + '\'' +
        ", description='" + description + '\'' +
        ", bankId='" + bankId + '\'' +
        ", cardTypeId='" + cardTypeId + '\'' +
        '}';
  }

  public String getBinNumber() {
    return binNumber;
  }

  public void setBinNumber(String binNumber) {
    this.binNumber = binNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  public String getCardTypeId() {
    return cardTypeId;
  }

  public void setCardTypeId(String cardTypeId) {
    this.cardTypeId = cardTypeId;
  }
}
