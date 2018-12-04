package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@GeneratePojoBuilder
@Document(collection = CollectionName.BUSINESS_LOGIC_RESPONSE)
public class BusinessLogicResponse extends BaseMongo {

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
    return "BusinessLogicResponse{" +
        "responseCode='" + responseCode + '\'' +
        ", responseMessage=" + responseMessage +
        '}';
  }
}
