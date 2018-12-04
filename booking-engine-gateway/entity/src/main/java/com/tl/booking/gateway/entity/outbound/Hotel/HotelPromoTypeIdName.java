package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelPromoTypeIdName extends CommonModel implements Serializable {
  private static final long serialVersionUID = 1L;

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
    return "HotelPromoTypeIdName{" +
        "id='" + id + '\'' +
        ", name=" + name +
        '}';
  }
}
