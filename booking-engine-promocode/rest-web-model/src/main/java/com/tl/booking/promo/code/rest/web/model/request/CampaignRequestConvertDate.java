package com.tl.booking.promo.code.rest.web.model.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class CampaignRequestConvertDate implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @NotBlank
  @NotEmpty
  private String name;

  @NotNull
  @NotBlank
  @NotEmpty
  private String code;

  @NotNull
  @NotBlank
  @NotEmpty
  private String promoCodeAdjustmentId;

  @Valid
  private List<CampaignPeriodRequestConvertDate> campaignPeriods;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getPromoCodeAdjustmentId() {
    return promoCodeAdjustmentId;
  }

  public void setPromoCodeAdjustmentId(String promoCodeAdjustmentId) {
    this.promoCodeAdjustmentId = promoCodeAdjustmentId;
  }

  public List<CampaignPeriodRequestConvertDate> getCampaignPeriods() {
    return campaignPeriods;
  }

  public void setCampaignPeriods(
      List<CampaignPeriodRequestConvertDate> campaignPeriods) {
    this.campaignPeriods = campaignPeriods;
  }

  @Override
  public String toString() {
    return "CampaignRequest{" +
        "name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", promoCodeAdjustmentId='" + promoCodeAdjustmentId + '\'' +
        ", campaignPeriods=" + campaignPeriods +
        '}' + super.toString();
  }
}
