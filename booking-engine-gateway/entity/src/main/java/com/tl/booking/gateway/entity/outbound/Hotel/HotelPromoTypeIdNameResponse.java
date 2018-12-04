package com.tl.booking.gateway.entity.outbound.Hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tl.booking.common.entity.CommonModel;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPromoTypeIdNameResponse extends CommonModel {

  private String id;

  private Map<String, String> name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, String> getName() {
    return name;
  }

  public void setName(Map<String, String> name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "HotelPromoTypeIdNameResponse{" +
        "id='" + id + '\'' +
        ", name=" + name +
        '}';
  }
}
