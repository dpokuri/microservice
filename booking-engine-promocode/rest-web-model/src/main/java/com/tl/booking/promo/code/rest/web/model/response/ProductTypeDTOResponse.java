package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;

public class ProductTypeDTOResponse extends BaseMongoResponse {

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
    return "ProductTypeDTOResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}' + super.toString();
  }
}
