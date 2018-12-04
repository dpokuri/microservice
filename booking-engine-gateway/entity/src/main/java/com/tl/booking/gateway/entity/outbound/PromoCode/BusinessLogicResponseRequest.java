package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class BusinessLogicResponseRequest extends CommonModel implements Serializable{

  @NotNull
  @NotBlank
  @NotEmpty
  @Length(min = 2, max = 200)
  private String responseCode;

  @NotNull
  @Valid
  private List<ResponseMessageRequest> responseMessage;

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public List<ResponseMessageRequest> getResponseMessage() {
    return responseMessage;
  }

  public void setResponseMessage(
      List<ResponseMessageRequest> responseMessage) {
    this.responseMessage = responseMessage;
  }

  @Override
  public String toString() {
    return "BusinessLogicResponseRequest{" +
        "responseCode='" + responseCode + '\'' +
        ", responseMessage=" + responseMessage +
        '}' + super.toString();
  }
}
