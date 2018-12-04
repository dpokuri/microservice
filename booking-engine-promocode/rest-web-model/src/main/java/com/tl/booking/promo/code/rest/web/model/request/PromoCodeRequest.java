package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class PromoCodeRequest extends CommonModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @NotEmpty
  @Length(min = 2, message = "The field must be at least 2 characters")
  @Length(max = 50, message = "The field must be less than 50 characters")
  @NotNull
  private String code;

  @NotBlank
  @NotEmpty
  @NotNull
  private String campaignId;

  @NotNull
  @Max(value = 99999999)
  @Min(value = 0)
  private Integer maxQty;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public Integer getMaxQty() {
    return maxQty;
  }

  public void setMaxQty(Integer maxQty) {
    this.maxQty = maxQty;
  }

  @Override
  public String toString() {
    return "PromoCodeRequest{" +
        "code='" + code + '\'' +
        ", campaignId='" + campaignId + '\'' +
        ", maxQty=" + maxQty +
        "} " + super.toString();
  }
}
