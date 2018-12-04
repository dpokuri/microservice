package com.tl.booking.gateway.entity.outbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HolidayResponse extends CommonModel implements Serializable {

  private static final long serialVersionUID = 1L;
  private String id;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private List<HolidayDescription> descriptions;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<HolidayDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(
      List<HolidayDescription> descriptions) {
    this.descriptions = descriptions;
  }

  @Override
  public String toString() {
    return "HolidayResponse{" +
        "id='" + id + '\'' +
        ", date=" + date +
        ", descriptions=" + descriptions +
        '}' + super.toString();
  }

}
