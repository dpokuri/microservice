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
public class CampaignRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  @Length(min = 2, max = 200)
  private String name;

  @NotNull
  @NotBlank
  @NotEmpty
  @Length(min = 2, max = 200)
  private String code;

  @NotNull
  @NotBlank
  @NotEmpty
  private String promoCodeAdjustmentId;

  @Valid
  private List<CampaignPeriodRequest> campaignPeriods;

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

  public List<CampaignPeriodRequest> getCampaignPeriods() {
    return campaignPeriods;
  }

  public void setCampaignPeriods(
      List<CampaignPeriodRequest> campaignPeriods) {
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
