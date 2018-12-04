package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Date;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CampaignPeriod extends CommonModel implements Serializable {

  private Date startDate;
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
        '}';
  }

}
