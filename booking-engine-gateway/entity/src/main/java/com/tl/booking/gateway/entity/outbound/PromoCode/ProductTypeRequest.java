package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class ProductTypeRequest extends CommonModel implements Serializable {

  @NotBlank
  @NotEmpty
  @NotNull
  private String description;

  @NotBlank
  @NotEmpty
  @NotNull
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "ProductTypeRequest{" +
        "id='" + description + '\'' +
        ", name='" + name + '\'' +
        '}' + super.toString();
  }

}
