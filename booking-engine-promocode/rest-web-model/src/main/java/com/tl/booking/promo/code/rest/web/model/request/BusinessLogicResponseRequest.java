package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.promo.code.entity.dao.ResponseMessageResponse;
import com.tl.booking.promo.code.rest.web.model.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class BusinessLogicResponseRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  String responseCode;

  @NotNull
  @NotEmpty
  List<ResponseMessageResponse> responseMessage;

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public List<ResponseMessageResponse> getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(
      List<ResponseMessageResponse> responseMessage) {
    this.responseMessage = responseMessage;
  }

  @Override
  public String toString() {
    return "BusinessLogicResponseRequest{" +
        "responseCode='" + responseCode + '\'' +
        ", responseMessage=" + responseMessage +
        '}';
  }
}
