package com.tl.booking.gateway.entity.outbound.PromoCode;

import java.util.List;

import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class BusinessLogicResponseResponse extends BaseMongoResponse {

  String responseCode;
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
    return "BusinessLogicResponseResponse{" +
        "responseCode='" + responseCode + '\'' +
        ", responseMessage=" + responseMessage +
        '}' + super.toString();
  }
}
