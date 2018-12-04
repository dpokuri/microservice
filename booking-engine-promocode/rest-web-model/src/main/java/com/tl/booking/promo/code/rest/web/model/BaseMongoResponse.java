package com.tl.booking.promo.code.rest.web.model;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.Objects;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class BaseMongoResponse extends CommonModel implements Serializable {

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public String toString() {
    return "BaseMongoResponse{" +
        "id='" + id + '\'' +
        '}' + super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BaseMongoResponse that = (BaseMongoResponse) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), id);
  }
}
