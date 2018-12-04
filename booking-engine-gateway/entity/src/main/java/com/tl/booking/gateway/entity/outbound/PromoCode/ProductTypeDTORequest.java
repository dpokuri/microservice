package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class ProductTypeDTORequest extends CommonModel implements Serializable {

  @NotBlank
  @NotEmpty
  @NotNull
  private String id;

  @NotBlank
  @NotEmpty
  @NotNull
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
    return "ProductTypeDTORequest{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}' + super.toString();
  }

}
