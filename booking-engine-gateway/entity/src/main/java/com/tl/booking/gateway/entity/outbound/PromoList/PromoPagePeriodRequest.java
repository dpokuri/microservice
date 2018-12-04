package com.tl.booking.gateway.entity.outbound.PromoList;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoPagePeriodRequest extends CommonModel implements Serializable {
  private String startDate;

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
    return "PromoPagePeriodRequest{" +
        "startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        '}' + super.toString();
  }

}
