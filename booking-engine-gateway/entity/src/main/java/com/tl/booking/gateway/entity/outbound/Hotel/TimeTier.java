package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.fields.TimeTierFields;

import org.springframework.data.mongodb.core.mapping.Field;

public class TimeTier extends CommonModel {

  @Field(value = TimeTierFields.START_TIME)
  private String startTime;

  @Field(value = TimeTierFields.END_TIME)
  private String endTime;

  @Field(value = TimeTierFields.PERCENTAGE)
  private Double percentage;

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Double getPercentage() {
    return percentage;
  }

  public void setPercentage(Double percentage) {
    this.percentage = percentage;
  }

  @Override
  public String toString() {
    return "TimeTier{" +
        "startTime='" + startTime + '\'' +
        ", endTime='" + endTime + '\'' +
        ", percentage=" + percentage +
        '}';
  }
}
