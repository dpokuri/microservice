package com.tl.booking.promo.code.rest.web.model.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class CampaignPeriodRequest implements Serializable {

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
