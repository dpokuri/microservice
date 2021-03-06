package com.tl.booking.gateway.entity.outbound.Hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PicRegionResponse extends CommonModel {

  private String id;

  private String name;

  private String email;

  private Integer active;

  private List<PicRegionDetail> region;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  public List<PicRegionDetail> getRegion() {
    return region;
  }

  public void setRegion(List<PicRegionDetail> region) {
    this.region = region;
  }

  @Override
  public String toString() {
    return "PicRegionResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", active=" + active +
        ", region=" + region +
        '}';
  }
}
