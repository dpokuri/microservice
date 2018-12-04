package com.tl.booking.image.rest.web.model.response;

import com.tl.booking.image.rest.web.model.CommonModel;
import java.io.Serializable;

public class BaseMongoResponse  extends CommonModel implements Serializable {
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "BaseMonggoResponse{" +
        "id='" + id + '\'' +
        '}' + super.toString();
  }

}
