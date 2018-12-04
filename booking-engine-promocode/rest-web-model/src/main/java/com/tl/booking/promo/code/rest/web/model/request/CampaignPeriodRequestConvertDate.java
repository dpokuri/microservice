package com.tl.booking.promo.code.rest.web.model.request;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;

public class CampaignPeriodRequestConvertDate implements Serializable {

  @NotNull
  private Date startDate;

  @NotNull
  private Date endDate;

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
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
