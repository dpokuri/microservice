package com.tl.booking.gateway.entity.outbound.Image;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;

public class ImagesResponse extends CommonModel implements Serializable {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return "ImagesRequest{" +
        "name='" + name + '\'' +
        '}' + super.toString();
  }
}
