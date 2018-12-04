package com.tl.booking.promo.code.rest.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class CampaignPeriodResponse implements Serializable {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date startDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    return "CampaignPeriod{" +
        "startDate=" + startDate +
        ", endDate=" + endDate +
        '}' + super.toString();
  }
}
