package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CampaignPeriodRequest extends CommonModel implements Serializable {

  @NotNull
  private String startDate;

  @NotNull
  private String endDate;

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "CampaignPeriodRequest{" +
        "startDate=" + startDate +
        ", endDate=" + endDate +
        '}' + super.toString();
  }
}
