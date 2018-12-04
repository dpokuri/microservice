package com.tl.booking.promo.code.libraries.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "timezone")
public class TimeZoneProperties {

  private Integer offset;
  private Integer offsetToDate;

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getOffsetToDate() {
    return offsetToDate;
  }

  public void setOffsetToDate(Integer offsetToDate) {
    this.offsetToDate = offsetToDate;
  }

  @Override
  public String toString() {
    return "TimeZoneProperties{" +
        "offset=" + offset +
        ", offsetToDate=" + offsetToDate +
        '}' + super.toString();
  }
}
