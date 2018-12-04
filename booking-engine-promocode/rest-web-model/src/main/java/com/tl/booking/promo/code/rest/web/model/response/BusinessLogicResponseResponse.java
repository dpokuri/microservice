package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.entity.ResponseMessage;
import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import java.util.List;

public class BusinessLogicResponseResponse extends BaseMongoResponse {

  String responseCode;
  List<ResponseMessage> responseMessage;

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public List<ResponseMessage> getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(
      List<ResponseMessage> responseMessage) {
    this.responseMessage = responseMessage;
  }

  @Override
  public String toString() {
    return "BusinessLogicResponse{" +
        "responseCode='" + responseCode + '\'' +
        ", responseMessage=" + responseMessage +
        '}';
  }
}
