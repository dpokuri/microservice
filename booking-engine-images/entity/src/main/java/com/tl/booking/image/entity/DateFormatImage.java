package com.tl.booking.image.entity;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class DateFormatImage extends CommonModel implements Serializable {
  private String year;
  private String month;
  private String day;

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  @Override
  public String toString() {
    return "DateFormatFile{" +
        "year=" + year +
        ", month=" + month +
        ", day=" + day +
        '}' + super.toString();
  }
}
