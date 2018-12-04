package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;

public class PromoCodeAdjustmentDropdownResponse extends CommonModel implements Serializable {

  private String id;
  private String name;

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

  @Override
  public String toString() {
    return "PromoCodeAdjustmentDropdownResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}' + super.toString();
  }
}
