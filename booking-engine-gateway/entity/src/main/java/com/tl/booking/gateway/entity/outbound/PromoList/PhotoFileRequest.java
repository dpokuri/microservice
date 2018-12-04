package com.tl.booking.gateway.entity.outbound.PromoList;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;


@GeneratePojoBuilder
public class PhotoFileRequest extends CommonModel implements Serializable {
  private String name;
  private String data;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "PhotoFileRequest{" +
        "name='" + name + '\'' +
        ", data='" + data + '\'' +
        '}' + super.toString();
  }
}
