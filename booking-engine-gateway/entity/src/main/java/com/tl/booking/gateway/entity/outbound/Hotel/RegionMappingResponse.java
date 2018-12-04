package com.tl.booking.gateway.entity.outbound.Hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionMappingResponse extends CommonModel {

  private String id;

  private String name;

  private Integer hotelCount;

  private Integer active;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getHotelCount() {
    return hotelCount;
  }

  public void setHotelCount(Integer hotelCount) {
    this.hotelCount = hotelCount;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "RegionMappingResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", hotelCount=" + hotelCount +
        ", active=" + active +
        '}';
  }
}
