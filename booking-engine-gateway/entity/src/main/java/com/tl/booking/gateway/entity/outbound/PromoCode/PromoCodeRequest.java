package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class PromoCodeRequest extends CommonModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @NotEmpty
  @NotNull
  @Length(min = 2, max = 200)
  private String code;

  @NotBlank
  @NotEmpty
  @NotNull
  @Length(min = 2, max = 200)
  private String campaignId;

  @NotNull
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
