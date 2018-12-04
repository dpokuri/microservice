package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class BankRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  private String name;

  @Override
  public String toString() {
    return "BankRequest{" +
        "name='" + name + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
